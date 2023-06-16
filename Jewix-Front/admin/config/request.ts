import { API } from '@/services/ant-design-pro/typings';
import { LocalToken } from '@/utils/token';
import { history, RequestConfig } from '@umijs/max';
import { message } from 'antd';
/**
 * @name 错误处理
 * pro 自带的错误处理， 可以在这里做自己的改动
 * @doc https://umijs.org/docs/max/request#配置
 */
export const requestConfig: RequestConfig = {
  // 错误处理： umi@3 的错误处理方案。
  errorConfig: {
    // 错误抛出
    errorThrower: (res) => {
      // 当服务器返回成功后，我们需要判断一下，是否真的成功。
      const { code, msg } = res as unknown as API.ResponseStructure;
      // console.log('res', res);
      // 抛出错误
      if (code >= 400) {
        const error: any = new Error(msg);
        error.name = 'CustomError';
        error.message = msg;
        error.code = code;
        throw error; // 抛出自制的错误
      }
    },
    // 错误接收及处理
    errorHandler: (error: any, opts?: any) => {
      // skipErrorHandler在请求时传入，用于跳过错误处理
      if (opts?.skipErrorHandler) throw error;
      if (error.name === 'CustomError') {
        // 我们的 errorThrower 抛出的错误。
        message.error(error.message);
        if (error.code === 401 || error.message === "登录状态失效,请重新登录") {
          LocalToken.remove();
          history.replace(`/login?redirect=${history.location.pathname.replace('/admin', '')}`);
        }
      } else if (error.response) {
        // Axios 的错误
        // 请求成功发出且服务器也响应了状态码，但状态代码超出了 2xx 的范围
        message.error(`Response status:${error.message}`);
      } else if (error.request) {
        // 请求已经成功发起，但没有收到响应
        // \`error.request\` 在浏览器中是 XMLHttpRequest 的实例，
        // 而在node.js中是 http.ClientRequest 的实例
        message.error('None response! Please retry.');
      } else {
        // 发送请求时出了点问题
        message.error('发送请求时出了点问题，请重试。');
      }
    },
  },
  baseURL: '/api/',
  requestInterceptors: [
    (url, options) => {
      // 添加 token 至请求头
      if (LocalToken.get()) {
        options.headers.token = LocalToken.get();
      }
      return {
        url,
        options,
      };
    },
  ],
  responseInterceptors: [
    // 一个二元组，第一个元素是 request 拦截器，第二个元素是错误处理
    [
      (response) => {
        requestConfig.errorConfig?.errorThrower?.(response.data);
        return response;
      },
      (error: any) => {
        return Promise.reject(error);
      },
    ],
  ],
};

import { API } from "@/services/ant-design-pro/typings";
import { Descriptions } from "antd";
import { HTMLAttributes, ReactElement } from "react";
type LogOperationDetailProps = HTMLAttributes<HTMLDivElement> & {
  data: API.FetchLogOperationResponse;
};
export default function LogOperationDetail(props: LogOperationDetailProps): ReactElement {
  const { data, ...rest } = props;
  return (
    <div className={`${rest.className ?? ""}`} {...rest}>
      <Descriptions layout="vertical">
        <Descriptions.Item label="JAVA方法" span={3}>
          {data.javaMethod}
        </Descriptions.Item>
        <Descriptions.Item label="操作请求数据" span={3}>
          {data.optReqMsg}
        </Descriptions.Item>
        <Descriptions.Item label="操作响应数据" span={3}>
          {data.optRespMsg}
        </Descriptions.Item>
      </Descriptions>
    </div>
  );
}

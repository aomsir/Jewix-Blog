import { BASE_URL } from '/config/proxy';
import { LocalToken } from '@/utils/token';
import { PlusOutlined } from '@ant-design/icons';
import { message, Upload, UploadFile, UploadProps } from 'antd';
import { FC, HTMLAttributes, useState } from 'react';
import { deletePhoto, getInsertImageUrl } from '@/services/api';
import css from "./UploadImage.module.scss";

type Props = HTMLAttributes<HTMLDivElement> & {
  onChange?: any;
  value?: UploadFile[];
  maxCount?: number;
  url?: string;
};
// 允许最大的上传数量

const UploadImage: FC<Props> = ({
  onChange,
  value = [],
  maxCount = 1,
  url = getInsertImageUrl(),
  ...rest
}) => {
  const [fileList, setFileList] = useState<UploadFile[]>(value);

  const _onChange: UploadProps['onChange'] = (props) => {
    let { fileList: newFileList, file } = props;
    /* 弹窗提示 */
    if (file?.response?.code === 0) {
      message.success('上传成功');
    }
    if (file.status === 'error') {
      message.error('上传失败');
    }
    // 删除错误的文件
    if (file?.response?.code === 1) {
      // 从新的文件列表中删除错误的文件
      newFileList = newFileList.filter((_file) => _file !== file);
      // 弹窗提示
      message.error(file.response.msg);
    }

    let _newFileList = newFileList
      .map((file) => {
        if (file.response) {
          // Component will show file.url as link
          // file.url = `${BASE_URL}/api/photos/download?name=${file.response.data}`;
          // file.thumbUrl = '';
        }

        return file;
      })
      .filter((file) => file.status !== 'error');

    setFileList(_newFileList);
    onChange(_newFileList);
  };

  return (
    <div {...rest}>
      <Upload
        className={css.uploadImage}
        fileList={fileList}
        action={url}
        listType="picture-card"
        onChange={_onChange}
        onRemove={async (file) => {
          console.log('remove', file);
          // await deletePhoto({ fileName: file.response });
        }}
        maxCount={maxCount}
        onDownload={() => {
        }}
        headers={{
          [LocalToken.tokenKey]: LocalToken.get() ?? '',
        }}
      >
        {fileList.length >= maxCount ? null : (
          <div>
            <PlusOutlined />
            <div style={{ marginTop: 8 }}>添加图片</div>
          </div>
        )}
      </Upload>
      <span className="desc">支持png,jpg,jpeg 等图片格式</span>
    </div>
  );
};

export default UploadImage;

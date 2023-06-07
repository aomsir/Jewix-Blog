import { ReactElement, useState } from 'react';
import { HTMLAttributes } from 'react';
import { Form, Select } from 'antd';
import RichTextEditor from '@/components/bases/editor/RichTextEditor';
import { ProFormSwitch, ProFormSelect, ProFormRadio } from '@ant-design/pro-components';
import UploadImage from '@/components/bases/upload/UploadImage';
import { ArticleEnums, PhotoEnums } from '@/config/enums';
import { getInsertImageUrl } from '@/services/api';
import { halfStart } from '@/utils/array';
import { map } from 'lodash';
type EditArticleFormProps = HTMLAttributes<HTMLDivElement> & {
  imageType: PhotoEnums.type;
  onChangeImageType: (imageType: PhotoEnums.type) => void;
};

export default function EditArticleForm({
  imageType,
  onChangeImageType,
  ...rest
}: EditArticleFormProps): ReactElement {
  return (
    <div className={`${rest.className ?? ''}`} {...rest}>
      <Form.Item label="选择类型">
        <Select
          value={imageType}
          onChange={(value) => onChangeImageType(value)}
          options={halfStart(
            map(PhotoEnums.type, (v, k) => ({
              label: v,
              value: parseInt(k),
            })),
          )}
        />
      </Form.Item>
      <Form.Item name="cover">
        <UploadImage
          maxCount={Infinity}
          className="uploadImage"
          url={getInsertImageUrl(imageType)}
        />
      </Form.Item>
    </div>
  );
}

import { PhotoEnums } from '@/config/enums';
import { useModelVisionModalForm } from '@/hooks/props';
import { API } from '@/services/ant-design-pro/typings';
import { deletePhoto, fetchPhotos, getFetchImageUrl } from '@/services/api';
import { halfStart } from '@/utils/array';
import { UploadOutlined } from '@ant-design/icons';
import {
  ActionType,
  ModalForm,
  PageContainer,
  ProList,
  ProListMetas,
} from '@ant-design/pro-components';
import { Button, Image, Row } from 'antd';
import { map } from 'lodash';
import { ReactElement, useEffect, useRef, useState } from 'react';
import { HTMLAttributes } from 'react';
import { fetchWidthNormalizedResponse } from '../article';
import EditPhotoForm from './components/EditPhotoForm';
import ImageCard from './components/ImageCard';
import css from './Photo.module.scss';
type PhotoProps = HTMLAttributes<HTMLDivElement>;
export default function Photo(props: PhotoProps): ReactElement {
  const { ...rest } = props;
  const [selectedType, setSelectedType] = useState<number>(PhotoEnums.type.又拍云);
  const actionRef = useRef<ActionType>(null);
  const { modalVisionState, modalVisionProps } = useModelVisionModalForm();

  useEffect(() => {
    actionRef.current?.reload();
  }, [selectedType]);

  return (
    <PageContainer
      // 标签页
      tabList={tabs}
      tabActiveKey={selectedType.toString()}
      onTabChange={(key) => setSelectedType(parseInt(key))}
      className={css.photo}
      extra={
        <Button type="primary" key={1} onClick={() => modalVisionState.setOpen(true)}>
          <UploadOutlined />
          上传
        </Button>
      }
      {...rest}
    >
      <ProList<API.FetchPhotosResponse, API.PaginationResponse>
        actionRef={actionRef}
        request={fetchWidthNormalizedResponse((params) =>
          fetchPhotos({ ...params, type: selectedType }),
        )}
        pagination={{
          defaultPageSize: 24,
          showSizeChanger: true,
          pageSizeOptions: [12, 24, 42],
        }}
        renderItem={(item) => (
          <ImageCard
            photo={item}
            onDelete={async () => {
              try {
                await deletePhoto({ type: selectedType, fileName: item.fileName });
                actionRef.current?.reload();
              } catch (error) {}
            }}
          />
        )}
      />
      <ModalForm
        title="新增图片"
        {...modalVisionProps}
        submitter={false}
        preserve={false}
        modalProps={{
          destroyOnClose: true,
          onCancel: () => {
            actionRef.current?.reload();
            modalVisionState.setOpen(false);
          },
        }}
      >
        <EditPhotoForm imageType={selectedType as any} onChangeImageType={setSelectedType as any} />
      </ModalForm>
    </PageContainer>
  );
}

const tabs = halfStart(
  map(PhotoEnums.type, (v, k) => ({
    tab: v,
    key: parseInt(k),
  })),
);

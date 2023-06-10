import { OPERATIONS } from "@/access";
import HasOperation from "@/components/bases/hasOperation/hasOperation";
import { PhotoEnums } from "@/config/enums";
import { useHasOperation } from "@/hooks/auth";
import { useModelVisionModalForm } from "@/hooks/props";
import { API } from "@/services/ant-design-pro/typings";
import { deletePhoto, fetchPhotos } from "@/services/api";
import { halfStart } from "@/utils/array";
import { UploadOutlined } from "@ant-design/icons";
import { ActionType, ModalForm, PageContainer, ProList } from "@ant-design/pro-components";
import { Button } from "antd";
import { map } from "lodash";
import { HTMLAttributes, ReactElement, useEffect, useRef, useState } from "react";
import { fetchWidthNormalizedResponse } from "../article";
import EditPhotoForm from "./components/EditPhotoForm";
import ImageCard from "./components/ImageCard";
import css from "./Photo.module.scss";
type PhotoProps = HTMLAttributes<HTMLDivElement>;
export default function Photo(props: PhotoProps): ReactElement {
  const { ...rest } = props;
  const [selectedType, setSelectedType] = useState<number>(PhotoEnums.type.又拍云);
  const actionRef = useRef<ActionType>(null);
  const { modalVisionState, modalVisionProps } = useModelVisionModalForm();

  useEffect(() => {
    actionRef.current?.reload();
  }, [selectedType]);

  const hasCreateOperation = useHasOperation(OPERATIONS.CREATE);

  return (
    <PageContainer
      // 标签页
      tabList={tabs}
      tabActiveKey={selectedType.toString()}
      onTabChange={(key) => setSelectedType(parseInt(key))}
      className={css.photo}
      extra={
        <HasOperation operation={OPERATIONS.CREATE}>
          <Button
            disabled={!hasCreateOperation}
            type="primary"
            key={1}
            onClick={() => modalVisionState.setOpen(true)}
          >
            <UploadOutlined />
            上传
          </Button>
        </HasOperation>
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

import { OPERATIONS } from "@/access";
import HasOperation from "@/components/bases/hasOperation/hasOperation";
import PopConfirmDelete from "@/components/bases/popConfirmDelete/PopConfirmDelete";
import { ArticleEnums } from "@/config/enums";
import { useModelVisionModalForm, useSelection } from "@/hooks/props";
import { API } from "@/services/ant-design-pro/typings";
import { fetchAllTags, insertTag } from "@/services/api";
import {
  deleteArticles,
  fetchArticleByUUID,
  fetchArticles,
  insertArticle,
  updateArticle,
} from "@/services/api/article";
import { fetchAllCategories } from "@/services/api/category";
import { md } from "@/utils/markdownIt";
import {
  ActionType,
  ModalForm,
  PageContainer,
  ProList,
  ProListMetas,
} from "@ant-design/pro-components";
import { Image, message, Space } from "antd";
import { HTMLAttributes, ReactElement, useEffect, useRef, useState } from "react";
import css from "./Article.module.scss";
import EditArticleForm from "./components/EditArticleForm";
import ProTableToolBar from "./components/ProTableToolBar";
import { tableConfig } from "/config/table";
type ArticleProps = HTMLAttributes<HTMLDivElement>;
// 文章列表页面
export default function Article({ className, ...rest }: ArticleProps): ReactElement {
  const { modalVisionState, modalVisionProps } = useModelVisionModalForm();
  const [selectionState, selectionProps] = useSelection();
  const [tags, setTags] = useState<API.FetchTagResponse[]>([]);
  const actionRef = useRef<ActionType>();
  const [categories, setCategories] = useState<API.FetchCategoryResponse[]>([]);
  const [initialValues, setInitialValues] = useState<API.FetchArticleDetailResponse>();

  useEffect(() => {
    (async () => {
      const tags = await fetchAllTags();
      setTags(tags);
      const categories = await fetchAllCategories();
      setCategories((categories.result as any) ?? []);
    })();
  }, []);
  // 清空初始值
  useEffect(() => {
    if (!modalVisionState.open) {
      setInitialValues(undefined);
    }
  }, [modalVisionState.open]);

  metas.actions!.render = (dom, entity) => {
    return (
      <Space>
        <HasOperation operation={OPERATIONS.UPDATE}>
          <a
            onClick={async () => {
              try {
                const data = await fetchArticleByUUID(entity.uuid);
                setInitialValues(data.result);
                modalVisionState.setOpen(true);
              } catch (error) {}
            }}
          >
            编辑
          </a>
        </HasOperation>
        <HasOperation operation={OPERATIONS.DELETE}>
          <PopConfirmDelete
            onConfirm={async () => {
              try {
                await deleteArticles({ ids: [entity.id] });
                message.success("删除成功");
                actionRef.current?.reload();
              } catch (error) {}
            }}
          />
        </HasOperation>
      </Space>
    );
  };

  return (
    <PageContainer className={`${className ?? ""} ${css.article}`} {...rest}>
      <ProList<API.FetchArticleResponse, API.PaginationParams>
        headerTitle="文章列表"
        toolBarRender={() => [
          <ProTableToolBar key={1} onInsertButtonClick={() => modalVisionState.setOpen(true)} />,
        ]}
        metas={metas}
        request={fetchWidthNormalizedResponse(fetchArticles)}
        pagination={{
          defaultPageSize: 10,
          showSizeChanger: true,
          pageSizeOptions: tableConfig.pageSizes,
        }}
        // 批量操作
        rowSelection={selectionProps}
        actionRef={actionRef}
        //批量删除
        tableAlertRender={({ selectedRowKeys, onCleanSelected }) => (
          <Space size={24}>
            <span>已选择 {selectedRowKeys.length} 项</span>
            <HasOperation operation={OPERATIONS.DELETE}>
              <PopConfirmDelete
                onConfirm={async () => {
                  try {
                    await deleteArticles({ ids: selectedRowKeys });
                    message.success("删除成功");
                    onCleanSelected();
                    actionRef.current?.reload();
                  } catch (error) {}
                }}
              >
                批量删除
              </PopConfirmDelete>
            </HasOperation>
          </Space>
        )}
      />
      <ModalForm
        title="发布文章"
        layout="horizontal"
        labelAlign="right"
        preserve={false}
        {...modalVisionProps}
        modalProps={{ className: css.editArticleDialog, destroyOnClose: true }}
        submitter={{
          searchConfig: {
            submitText: "发布",
            resetText: "取消",
          },
        }}
        initialValues={initialValues}
        onFinish={async (values: API.InsertArticleBody | API.UpdateArticleBody) => {
          try {
            let _article = {
              ...values,
              isTop: values.isTop ? ArticleEnums.IsTop.是 : ArticleEnums.IsTop.否,
              description:
                values.description ||
                md
                  .render(values.content)
                  .replace(/<[^>]+>/g, "")
                  .substring(0, 30),
            };
            // 新增标签
            for (const index in values.tagIds) {
              if (values.tagIds[index]) {
                const tag = values.tagIds[index];
                if (typeof tag === "string") {
                  const {
                    result: { id },
                  } = await insertTag({ tagName: tag });

                  values.tagIds[index] = id;
                }
              }
            }
            // 提交表单
            if (!initialValues) {
              await insertArticle(_article as API.InsertArticleBody);
              actionRef.current?.reload();
              message.success("发布成功");
            } else {
              await updateArticle(_article as API.UpdateArticleBody);
              actionRef.current?.reload();
              message.success("修改成功");
            }
            return true;
          } catch (error) {
            return false;
          }
        }}
      >
        <EditArticleForm
          initialValues={initialValues}
          tags={tags}
          categories={categories}
          isUpdateForm={!!initialValues}
        />
      </ModalForm>
    </PageContainer>
  );
}
/**
 * 将接口返回的数据格式化为ProList/ProTable需要的格式
 */
export function fetchWidthNormalizedResponse<P>(
  fetchFunc: (params: P) => Promise<API.ResponseStructure<API.PaginationResponse>>,
) {
  return async (params: P) => {
    const data = await fetchFunc(params);

    return {
      data: data.result?.list ?? (data.result as unknown as unknown[]),
      success: data.msg === "success",
      total: data.result?.totalCount,
    };
  };
}
const metas: ProListMetas<API.FetchArticleResponse> = {
  title: {
    dataIndex: "title",
  },
  avatar: {
    render(_, entity) {
      return (
        <Image
          width={160}
          height={90}
          src={entity.cover}
          alt={entity.title}
          fallback="/admin/notFound.png"
        />
      );
    },
  },
  description: {
    dataIndex: "description",
  },
  actions: {},
};

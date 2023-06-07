import { deleteFactory, fetchFactory, insertFactory, updateFactory } from '@/utils/request';
import { API } from '../ant-design-pro/typings';

export const insertComment = insertFactory<API.InsertCommentParams>('admin/comments');
export const fetchComments = fetchFactory<API.FetchCommentResponse, API.FetchCommentParams>(
  'admin/comments',
);
export const updateCommentStatus =
  updateFactory<API.UpdateCommentStatusParams>('admin/comments/status');
export const deleteComment = deleteFactory<API.DeleteCommentParams>('admin/comments');
export const updateComment = updateFactory<API.UpdateCommentParams>('admin/comments');

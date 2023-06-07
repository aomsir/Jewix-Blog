import { useState } from 'react';
/**
 * 用于表单的弹窗
 */
export function useModelVisionModalForm({ visible = false } = {}) {
  const [open, setOpen] = useState<boolean>(visible);

  return {
    modalVisionState: {
      open,
      setOpen,
    },
    modalVisionProps: {
      open,
      onOpenChange(visible: boolean) {
        setOpen(visible);
      },
    },
  };
}
// 用于表格/列表的选择
export function useSelection() {
  const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([]);

  return [
    {
      selectedRowKeys,
      setSelectedRowKeys,
    },
    {
      selectedRowKeys,
      onChange(newSelectedRowKeys: React.Key[]) {
        setSelectedRowKeys(newSelectedRowKeys);
      },
    },
  ] as const;
}

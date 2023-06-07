import { SwapOutlined } from '@ant-design/icons';
import { Button } from 'antd';
import { HTMLAttributes, ReactElement, useState } from 'react';
import css from './Editor.module.scss';
import MarkDownEditor from './MarkDownEditor';
import RichTextEditor from './RichTextEditor';
type EditorProps = HTMLAttributes<HTMLDivElement> & {
  value?: string;
  onChange?: (value: string) => void;
};
export default function Editor<T>(props: EditorProps): ReactElement {
  const { value, onChange, ...rest } = props;

  const [type, setType] = useState(Type.富文本);

  const toType = (type + 1) % 2;

  return (
    <div className={`${rest.className ?? ''} ${css.Editor}`} {...rest}>
      {/* 切换按钮 */}
      <Button
        className="switchButton"
        icon={<SwapOutlined />}
        onClick={() => {
          setType(toType);
        }}
      >
        {Type[toType]}
      </Button>
      {type === Type.富文本 && <RichTextEditor initialValue={value} onChange={onChange!} />}
      {type === Type.markdown && <MarkDownEditor initialValue={value} onChange={onChange!} />}
    </div>
  );
}

enum Type {
  '富文本',
  'markdown',
}

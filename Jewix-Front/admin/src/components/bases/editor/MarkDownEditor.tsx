import { md } from '@/utils/markdownIt';
import { HTMLAttributes, ReactElement, useState } from 'react';
import css from './MarkDownEditor.module.scss';
type MarkDownEditorProps = HTMLAttributes<HTMLDivElement> & {
  initialValue?: string;
  onChange: (value: string) => void;
};
export default function MarkDownEditor(props: MarkDownEditorProps): ReactElement {
  const { initialValue, onChange, ...rest } = props;
  const [content, setContent] = useState(initialValue);

  return (
    <div className={`${rest.className ?? 'markdown-body'} ${css.MarkDownEditor}`} {...rest}>
      <div className="toolbars"></div>
      <div className="editorInner">
        <textarea
          className="markdown-editor"
          placeholder="在这里输入文章内容"
          // value={content}
          defaultValue={initialValue}
          onInput={(e) => {
            setContent((e.target as HTMLInputElement).value);
            onChange?.((e.target as HTMLInputElement).value);
          }}
        ></textarea>
        {/* 实时预览 */}
        <div
          className="preview"
          dangerouslySetInnerHTML={{ __html: md.render(content ?? '') }}
        ></div>
      </div>
    </div>
  );
}

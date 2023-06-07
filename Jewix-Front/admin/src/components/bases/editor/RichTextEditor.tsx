import { HTMLAttributes, ReactElement } from 'react';
import BundledEditor from './BundledEditor';
import css from './RichTextEditor.module.scss';

type RichTextEditorProps = HTMLAttributes<HTMLDivElement> & {
  initialValue?: string;
  onChange: (value: string) => void;
};
export default function RichTextEditor(props: RichTextEditorProps): ReactElement {
  const { initialValue, onChange, ...rest } = props;
  // const editorRef = useRef<TinyMCEEditor | null>(null);
  return (
    <div className={css['rich-text-editor']} {...rest}>
      <BundledEditor
        onChange={(evt) => {
          onChange?.(evt.target.getContent());
        }}
        apiKey="76wtzy0nz4uxfm0xw019htnpx6py715xe3rmq0f94zufrlmt"
        // onInit={(evt, editor) => {
        //   editor.on('input', (e) => {
        //     setValue(editor.getContent());
        //   });
        // }}
        initialValue={initialValue ?? ''}
        init={{
          menubar: false,
          plugins: [
            'advlist',
            'autolink',
            'lists',
            'link',
            'image',
            'charmap',
            'anchor',
            'searchreplace',
            'fullscreen',
            'insertdatetime',
            'media',
            'table',
            'help',
            'wordcount',
            'codesample',
            'emoticons',
          ],
          toolbar:
            'undo redo | blocks | ' +
            'bold italic forecolor underline strikethrough | alignleft aligncenter ' +
            'alignright alignjustify | bullist numlist outdent indent | link  image media |' +
            'table searchreplace codesample emoticons fullscreen |' +
            'removeformat help',
          content_style: 'body { font-family:Helvetica,Arial,sans-serif; font-size:14px }',
          setup(editor) {
            editor.on('keydown', function (e) {
              if (e.key === 'Tab' && !e.ctrlKey && !e.altKey) {
                e.preventDefault();
                e.stopPropagation();
                // 按下tab键添加四个空格
                editor.insertContent('&nbsp;&nbsp;&nbsp;&nbsp;');
              }
            });
          },
        }}
      />
    </div>
  );
}

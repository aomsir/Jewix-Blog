import { ReactElement, useState } from 'react';
import { Image, Tooltip } from 'antd';
import { HTMLAttributes } from 'react';
import css from './PopImage.module.scss';
type PopImageProps = HTMLAttributes<HTMLDivElement> & {
  src: string;
};
export default function PopImage(props: PopImageProps): ReactElement {
  const { src, ...rest } = props;
  const [visible, setVisible] = useState(false);

  return (
    <div className={`${rest.className ?? ''} ${css.popImage}`} {...rest}>
      <Tooltip title={src}>
        <p onClick={() => setVisible(true)}>{src}</p>
      </Tooltip>
      <Image
        style={{ display: 'hidden' }}
        src={src}
        preview={{
          visible,
          src: src,
          onVisibleChange: (value) => {
            setVisible(value);
          },
        }}
      />
    </div>
  );
}

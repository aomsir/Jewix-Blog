import { fetchSetting } from '@/services/api';
import { PageContainer } from '@ant-design/pro-components';
import { ReactElement, useEffect } from 'react';
import { HTMLAttributes } from 'react';
type SettingProps = HTMLAttributes<HTMLDivElement>;
export default function Setting(props: SettingProps): ReactElement {
  const { ...rest } = props;

  useEffect(() => {
    fetchSetting({ type: -1 })
      .then((res) => {
        console.log(res);
      })
      .catch((err) => {});
  });

  return (
    <PageContainer className={`${rest.className ?? ''}`} {...rest}>
      Setting
    </PageContainer>
  );
}

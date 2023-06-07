import { useEffect, useState } from 'react';

export function useFetch<R>(fetchFunc: (...arg: any) => Promise<R>) {
  const [data, setData] = useState<R>();

  const loadData = () => {
    (async () => {
      setData(await fetchFunc());
    })();
  };

  useEffect(() => {
    loadData();
  }, []);

  return [data, setData, loadData] as [R, typeof setData, typeof loadData];
}

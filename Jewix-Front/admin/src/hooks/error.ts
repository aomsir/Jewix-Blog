import { useEffect, useState } from 'react';
// 错误边界
export function useErrorBoundary() {
  const [error, setError] = useState<Error | null>(null);

  const errorHandler = (e: ErrorEvent) => {
    console.log(e);
    setError(e.error);
  };

  useEffect(() => {
    window.addEventListener('error', errorHandler);
    return () => {
      window.removeEventListener('error', errorHandler);
    };
  }, []);

  return error;
}

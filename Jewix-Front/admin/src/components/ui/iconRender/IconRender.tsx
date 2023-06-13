import * as icons from "@ant-design/icons";

export default function IconRender(props: { name: string }) {
  // const [Icon, setIcon] = useState<() => ReactElement | null>(() => null);

  // useEffect(() => {
  //   import(`@ant-design/icons/es/icons/${props.name}.js`).then((icon) => {
  //     setIcon(() => icon.default);
  //   });
  // }, [props.name]);
  if (!props.name) return;
  // @ts-ignore
  const Icon = icons[props.name[0].toUpperCase() + props.name.slice(1)] ?? (() => null);

  return <Icon />;
}

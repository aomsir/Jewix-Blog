import Image from "next/image"
import { ReactElement } from "react"
import { Swiper, SwiperSlide } from "swiper/react"
import { Pagination, Mousewheel, Autoplay } from "swiper"
import css from "./CardCarousel.module.scss"
import Link from "next/link"
export interface CardCarouselProps {
    items: { cover: string; title: string; uuid: string }[]
    [key: string]: any
}
export default function CardCarousel({ items, ...rest }: CardCarouselProps): ReactElement {
    return (
        <div className={`${css.card}`} {...rest}>
            <Swiper modules={[Pagination, Mousewheel, Autoplay]} pagination={{ clickable: true }} mousewheel autoplay>
                {items.map(({ cover, title, uuid }, i) => (
                    <SwiperSlide key={i}>
                        <Link href={`/detail/${uuid}`}>
                            <div className="img">
                                <Image
                                    src={cover ?? "/notFound.png"}
                                    loader={p => p.src ?? "/notFound.png"}
                                    alt=""
                                    width={360}
                                    height={180}
                                    onError={e => ((e.target as HTMLImageElement).srcset = "/notFound.png")}
                                />
                            </div>
                        </Link>
                        <p>{title}</p>
                    </SwiperSlide>
                ))}
            </Swiper>
        </div>
    )
}

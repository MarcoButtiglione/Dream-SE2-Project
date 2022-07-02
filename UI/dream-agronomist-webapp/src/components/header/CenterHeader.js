import {
    Header,
    HeaderBrand,
    HeaderContent,
    HeaderRightZone,
    HeaderSearch,
    HeaderSocialsZone,
    Icon
} from "design-react-kit";
/**
 *Center Header
 */
export function CenterHeader(){
    return(
        <Header
            theme=""
            type="center"
        >
            <HeaderContent>
                <HeaderBrand iconName="it-code-circle">
                    <h2>
                        Dream
                    </h2>
                    <h3>
                        Telegana farm monitoring platform
                    </h3>
                </HeaderBrand>

            </HeaderContent>
        </Header>
    )
}
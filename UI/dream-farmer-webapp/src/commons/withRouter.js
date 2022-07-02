import { useNavigate } from 'react-router-dom';

/**
 * Wrapper used to add the useNavigate Hook in a React Class Component
 */
export const withRouter = (Component) => {
    const Wrapper = (props) => {
        const navigate = useNavigate();

        return (
            <Component
                navigate={navigate}
                {...props}
            />
        );
    };

    return Wrapper;
};
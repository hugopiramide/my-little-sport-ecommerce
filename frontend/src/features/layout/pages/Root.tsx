import { Outlet } from 'react-router-dom';
import Navbar from '../components/Navbar';
import Footer from '../components/Footer';
import Breadcrumbs from '../components/Breadcrumbs/Breadcrumbs';

const Root = () => {
    return(
        <>
            <Navbar />
            <Breadcrumbs />
            <main>
                <Outlet />
            </main>
            <Footer />
        </>
    )
}

export default Root
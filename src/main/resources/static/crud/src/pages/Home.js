import React from 'react';
import './index.css'

function Home() {
    return (
        <div>
            <div className="main">
                <div className="main-header">
                    <div className="nav-button but-hol">
                        <span className="nos"></span>
                        <span className="ncs"></span>
                        <span className="nbs"></span>
                        <div className="menu-button-text">Menu</div>
                    </div>

                    <div className="header-social">
                        <ul className='ul'>
                            <li><a href="http://localhost:3000/login"><i className="bi bi-facebook"></i></a></li>
                            <li><a href="http://localhost:3000/register"><i className="bi bi-twitter"></i></a></li>
                            <li><a href="http://localhost:3000/admin-page"><i className="bi bi-dribbble"></i></a></li>
                            <li><a href="#"><i className="bi bi-instagram"></i></a></li>
                        </ul>
                    </div>
                    <div className="folio-btn">
                        <a className="folio-btn-item ajax" href="">
                            <span className="folio-btn-dot"></span>
                            <span className="folio-btn-dot"></span>
                            <span className="folio-btn-dot"></span>
                            <span className="folio-btn-dot"></span>
                            <span className="folio-btn-dot"></span>
                            <span className="folio-btn-dot"></span>
                            <span className="folio-btn-dot"></span>
                            <span className="folio-btn-dot"></span>
                            <span className="folio-btn-dot"></span>
                        </a>
                    </div>
                </div>

                <div id="wrapper">
                    <div className="content-holder">
                        <div className="hero-wrap fl-wrap full-height scroll-con-sec">

                            <div className="impulse-wrap">
                                <div className="mm-par-wrap">
                                    <div className="mm-parallax" data-tilt data-tilt-max="4" >
                                        <div className="overlay"></div>
                                        <div className="bg" >

                                        </div>

                                        <div className="hero-wrap-item fl-wrap">
                                            <div className="container">
                                                <div className="fl-wrap section-entry hiddec-anim" style={{opacity: 1}}>
                                                <h1 className="BoardName text-center">Welcome to My Page</h1>

                                            </div></div></div>

                                    </div>

                                </div>
                            </div>

                            <div className="hero-corner hiddec-anim" style={{opacity: 1, transform: "translate3d(0px, 0px, 0px)"}}></div>

                            <div className="hero-corner2 hiddec-anim" style={{opacity: 1, transform: "translate3d(0px, 0px, 0px)"}}></div>

                            <div className="hero-corner3 hiddec-anim" style={{opacity: 1, transform: "translate3d(0px, 0px, 0px)"}}></div>

                            <div className="hero-corner4 hiddec-anim" style={{opacity: 1, transform: "translate3d(0px, 0px, 0px)"}}></div>


                        </div>
                    </div>
                </div>

            </div>
        </div>
    );
}

export default Home;
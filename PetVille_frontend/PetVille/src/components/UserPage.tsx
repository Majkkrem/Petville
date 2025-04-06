import CustomFooter from "./CustomFooter";


function UserPage() {
    return (
        <div>
            <div className="container-fluid">
                <div className="row justify-content-center">
                    <div className="col-2 side-border-left"></div>
                    <div className="col-8 main-content">
                        <main className="my-4">
                            <section id="user-profile" className="mb-5 text-center">
                                <div className="profile-header">
                                    <h2 id="username">Username</h2>
                                    <div className="badge badge-primary p-2">Member since: <span id="join-date">2025-01-01</span></div>
                                </div>
                            </section>

                            <section id="my-pets" className="mb-5">
                                <h3 className="mb-4">My Pets</h3>
                                <div className="row" id="pets-container">
                                    <div className="row">
                                        <div className="col-md-4 mb-3">
                                            <div className="card">
                                                <div className="card-body">
                                                    <h6 className="card-subtitle mb-2 text-muted">Pets Owned</h6>
                                                    <p className="h4" id="pets-owned"></p>
                                                </div>
                                            </div>
                                        </div>

                                        <div className="col-md-4 mb-3">
                                            <div className="card">
                                                <div className="card-body">
                                                    <h6 className="card-subtitle mb-2 text-muted">High Score</h6>
                                                    <p className="h4" id="high-score">0</p>
                                                </div>
                                            </div>
                                        </div>

                                        <div className="col-md-4 mb-3">
                                            <div className="card">
                                                <div className="card-body">
                                                    <h6 className="card-subtitle mb-2 text-muted">Total Play Time</h6>
                                                    <p className="h4" id="play-time">0 hours</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </section>
                        </main>
                    </div>
                    <div className="col-2 side-border-right"></div>
                </div>

                
            </div>
            <CustomFooter />
        </div>
);
}

export default UserPage;
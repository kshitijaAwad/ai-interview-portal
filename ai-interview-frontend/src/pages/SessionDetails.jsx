import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import api from "../api/axiosConfig";
import Navbar from "../components/Navbar";

function SessionDetails() {

    const { sessionId } = useParams();

    const [session, setSession] = useState(null);

    useEffect(() => {
        loadSession();
    }, []);

    const loadSession = async () => {

        try {

            const response = await api.get(
                `/api/interview/session/${sessionId}`
            );

            setSession(response.data);

        } catch (error) {

            console.error(error);
        }
    };

   if (!session) {
    return (
        <>
            <Navbar />
            <div className="container mt-5">
                <div className="alert alert-info">
                    Loading Interview Details...
                </div>
            </div>
        </>
    );
}

    return (
        <>
        < Navbar/>
        <div className="container mt-5">

            <div className="card shadow mb-4">

            <div className="card-body">

                <h2>Interview Details</h2>

                <p>
                    <strong>Session ID:</strong> {session.sessionId}
                </p>

                <p>
                   <strong>Domain:</strong> {session.domain}
                </p>

                <p>
                    <strong>Difficulty:</strong> {session.difficulty}
                </p>

            </div>

        </div>

            {session.questions.map((q) => (

                <div
                    key={q.questionId}
                    className="card mb-3"
                >
                    <div className="card-body">

                        <h5>
                            {q.question}
                        </h5>

                        <p>
                            <strong>Answer:</strong>{" "}
                            {q.answer || "Not Answered"}
                        </p>

                        <p>

                            <strong>Score:</strong>{" "}

                            {q.score != null ? (

                                <span className="badge bg-success">
                                    {q.score}/10
                                </span>

                            ) : (

                                <span className="badge bg-secondary">
                                    Not Evaluated
                                </span>

                            )}

                            </p>

                            <div className="alert alert-light mt-2">

                            <strong>Feedback:</strong>

                            <br />

                            {q.feedback || "No feedback available"}

                        </div>

                    </div>
                </div>

            ))}

        </div>
        </>
    );
}

export default SessionDetails;
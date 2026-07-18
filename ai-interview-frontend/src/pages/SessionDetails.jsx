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
        return <h3>Loading...</h3>;
    }

    return (
        <>
        < Navbar/>
        <div className="container mt-5">

            <h2>Interview Details</h2>

            <h5>Domain: {session.domain}</h5>

            <h5>Difficulty: {session.difficulty}</h5>

            <hr />

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
                            {q.score ?? "-"}
                        </p>

                        <p>
                            <strong>Feedback:</strong>{" "}
                            {q.feedback || "-"}
                        </p>

                    </div>
                </div>

            ))}

        </div>
        </>
    );
}

export default SessionDetails;
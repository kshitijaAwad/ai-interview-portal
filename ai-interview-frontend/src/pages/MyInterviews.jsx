import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/axiosConfig";
import Navbar from "../components/Navbar";

function MyInterviews() {

    const [sessions, setSessions] = useState([]);

    const navigate = useNavigate();

    useEffect(() => {
        loadSessions();
    }, []);

    const loadSessions = async () => {

        try {

            const response = await api.get(
                "/api/interview/my-sessions"
            );

            setSessions(response.data);

        } catch (error) {

            console.error(error);

            alert("Failed to load interviews");
        }
    };

    return (
        <>
            <Navbar />
        <div className="container mt-5">

            <h2>My Interviews</h2>

            <table className="table table-bordered mt-4">

                <thead>
                    <tr>
                        <th>Session ID</th>
                        <th>Domain</th>
                        <th>Difficulty</th>
                        <th>Action</th>
                    </tr>
                </thead>

                <tbody>

                    {sessions.map((session) => (

                        <tr key={session.sessionId}>

                            <td>{session.sessionId}</td>

                            <td>{session.domain}</td>

                            <td>{session.difficulty}</td>

                            <td>

                                <button
                                    className="btn btn-primary"
                                    onClick={() =>
                                        navigate(
                                            `/session/${session.sessionId}`
                                        )
                                    }
                                >
                                    View
                                </button>

                            </td>

                        </tr>

                    ))}

                </tbody>

            </table>

        </div>
        </>
    );
}

export default MyInterviews;
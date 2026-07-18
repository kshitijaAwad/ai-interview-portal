import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/axiosConfig";
import Navbar from "../components/Navbar";

function Dashboard() {

  const navigate = useNavigate();

  const [dashboard, setDashboard] = useState({
    totalInterviews: 0,
    totalQuestions: 0,
    averageScore: 0
  });

  useEffect(() => {
    loadDashboard();
  }, []);

  const loadDashboard = async () => {

    try {

      const response = await api.get(
        "/api/interview/dashboard"
      );

      setDashboard(response.data);

    } catch (error) {
      console.error(error);
    }
  };

  
  return (
    <>
        <Navbar />

    <div className="container mt-5">

      <div className="mb-4">
          <h2>Dashboard</h2>
          <p>Welcome to AI Interview Portal</p>
      </div>

      <div className="row">

        <div className="col-md-4">

          <div className="card text-center shadow">

            <div className="card-body">

              <h5>Total Interviews</h5>

              <h2>
                {dashboard.totalInterviews}
              </h2>

            </div>

          </div>

        </div>

        <div className="col-md-4">

          <div className="card text-center shadow">

            <div className="card-body">

              <h5>Total Questions</h5>

              <h2>
                {dashboard.totalQuestions}
              </h2>

            </div>

          </div>

        </div>

        <div className="col-md-4">

          <div className="card text-center shadow">

            <div className="card-body">

              <h5>Average Score</h5>

              <h2>
                {dashboard.averageScore}
              </h2>

            </div>

          </div>

        </div>

      </div>

      <div className="mt-5">

        <button
          className="btn btn-primary me-3"
          onClick={() =>
            navigate("/generate")
          }
        >
          Generate Interview
        </button>

        <button
          className="btn btn-success"
          onClick={() =>
            navigate("/my-interviews")
          }
        >
          My Interviews
        </button>

      </div>

    </div>
    </>
  );
}

export default Dashboard;
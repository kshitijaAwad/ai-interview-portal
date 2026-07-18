import { useState } from "react";
import api from "../api/axiosConfig";
import Navbar from "../components/Navbar";

function GenerateInterview() {

  const [domain, setDomain] = useState("");
  const [difficulty, setDifficulty] = useState("BEGINNER");

  const [questions, setQuestions] = useState([]);
  const [sessionId, setSessionId] = useState(null);
  
  const [answers, setAnswers] = useState({});
  const [results, setResults] = useState({});

  const generateInterview = async () => {

    try {

      const response = await api.post(
        "/api/interview/generate",
        {
          domain,
          difficulty
        }
      );

      setQuestions(response.data.questions);
      setSessionId(response.data.sessionId);

    } catch (error) {

      console.error(error);

      alert("Failed to generate interview");
    }
  };

  const submitAnswer = async (questionId) => {

  try {

    const response = await api.post(
      "/api/interview/submit-answer",
      {
        questionId: questionId,
        answer: answers[questionId]
      }
    );

    setResults({
      ...results,
      [questionId]: response.data
    });

  } catch (error) {

    console.error(error);

    alert("Failed to submit answer");
  }
};

  return (
      <>
        <Navbar />
    <div className="container mt-5">

      <h2>Generate Interview</h2>

      <input
        className="form-control mb-3"
        placeholder="Enter Domain"
        value={domain}
        onChange={(e) =>
          setDomain(e.target.value)
        }
      />

      <select
        className="form-select mb-3"
        value={difficulty}
        onChange={(e) =>
          setDifficulty(e.target.value)
        }
      >
        <option value="BEGINNER">
          BEGINNER
        </option>

        <option value="INTERMEDIATE">
          INTERMEDIATE
        </option>

        <option value="ADVANCED">
          ADVANCED
        </option>

      </select>

      <button
        className="btn btn-primary"
        onClick={generateInterview}
      >
        Generate Questions
      </button>

      {sessionId && (

        <div className="mt-4">

          <h5>
            Session ID: {sessionId}
          </h5>

        </div>

      )}

      {questions.length > 0 && (

        <div className="mt-4">

          <h4>Questions</h4>

          <div className="mt-4">

  {questions.map((question, index) => (

    <div
      key={question.questionId}
      className="card mb-3"
    >

      <div className="card-body">

        <h5>
          Q{index + 1}
        </h5>

        <p>
          {question.question}
        </p>

        <textarea
          className="form-control mb-3"
          rows="4"
          placeholder="Write your answer..."
          onChange={(e) =>
            setAnswers({
              ...answers,
              [question.questionId]: e.target.value
            })
          }
        />

        <button
          className="btn btn-success"
          onClick={() =>
            submitAnswer(
              question.questionId
            )
          }
        >
          Submit Answer
        </button>

        {results[question.questionId] && (

          <div className="mt-3">

            <h6>
              Score:
              {" "}
              {results[question.questionId].score}
            </h6>

            <p>
              <strong>
                Feedback:
              </strong>
              {" "}
              {results[question.questionId].feedback}
            </p>

          </div>

        )}

      </div>

    </div>

  ))}

</div>

        </div>

      )}

    </div>
    </>
  );
}

export default GenerateInterview;
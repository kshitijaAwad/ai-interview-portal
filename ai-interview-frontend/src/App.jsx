import { BrowserRouter, Routes, Route } from "react-router-dom";

import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";

import GenerateInterview from "./pages/GenerateInterview";
import MyInterviews from "./pages/MyInterviews";

import SessionDetails from "./pages/SessionDetails";

import ProtectedRoute from "./components/ProtectedRoute";
function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route
          path="/"
          element={<Login />}
        />

        <Route
          path="/register"
          element={<Register />}
        />

        <Route
          path="/dashboard"
          element={
              <ProtectedRoute>
                  <Dashboard />
              </ProtectedRoute>
          }
        />

        <Route
          path="/generate"
          element={
              <ProtectedRoute>
                  <GenerateInterview />
              </ProtectedRoute>
          }
        />

        <Route
          path="/my-interviews"
          element={
            <ProtectedRoute>
              <MyInterviews />
            </ProtectedRoute>
          }
        />

        <Route
          path="/session/:sessionId"
          element={
            <ProtectedRoute>
              <SessionDetails />
            </ProtectedRoute>
          }
        />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
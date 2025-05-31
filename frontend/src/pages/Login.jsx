import { useState } from "react";
import Box from "@mui/material/Box";
import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import { InputAdornment, IconButton } from "@mui/material";
import { Visibility, VisibilityOff } from "@mui/icons-material";
import { useNavigate } from "react-router-dom";
// import backgroundImage from "../assets/Career.jpg";
// import CodingImage from "../assets/Coding.jpg";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const navigate = useNavigate();

  const handleTogglePassword = () => {
    setShowPassword((prev) => !prev);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setErrorMessage("");
    console.log("submitted", { email, password });
    // navigate("/dashboard");
    // try {
    //   const response = await fetch("https://careerwale.com/con/login/", {
    //     method: "POST",
    //     headers: {
    //       "Content-Type": "application/json",
    //     },
    //     body: JSON.stringify({ email, password }),
    //   });

    //   const data = await response.json();

    //   if (response.ok) {
    //     console.log("Login successful:", data);
    //     navigate("/dashboard");
    //   } else {
    //     setErrorMessage(data.message || "Invalid email or password");
    //   }
    // } catch (error) {
    //   console.log("Error", error);
    //   setErrorMessage("Something went wrong.Please try again.");
    // }
  };

  return (
    <Box
      sx={{
        minHeight: "100vh",
        width: "100%",
        // backgroundImage: `url(${backgroundImage})`, // Replace with your background image
        // backgroundSize: "cover",
        // backgroundPosition: "center",
        // backgroundRepeat: "no-repeat",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      {/* <Box
        component="img"
        // src={require("./logo.png")}
        alt="Top Right"
        sx={{
          position: "absolute",
          top: 40,
          right: 150,
          width: 150,
          height: 60,
          fontSize: "120px",
          fontWeight: 900,
        }}
      /> */}
      <Box
        sx={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          flexWrap: "wrap",

          "& > :not(style)": {
            width: 420,
            height: 430,
            // paddingLeft: 5,
            textAlign: "center",
            // backgroundColor: "rgb(234, 238, 240)",
          },
        }}
      >
        {/* <Paper elevation={3}>
          <img
            src={CodingImage}
            alt="Login"
            style={{ width: "100%", height: "100%", objectFit: "cover" }}
          />
        </Paper> */}

        <Paper elevation={3}>
          <Typography
            component="h3"
            variant="h5"
            sx={{
              fontWeight: 700,
              marginTop: 7,
              // marginLeft: -27,
              "& > :not(style)": {
                width: 488,
                height: 388,
              },
            }}
          >
            Login
          </Typography>

          <form onSubmit={handleSubmit}>
            <TextField
              label="Email"
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              sx={{
                mt: 5, // margin-top
                width: 295,

                // marginLeft: 8,
                "& .MuiOutlinedInput-root": {
                  // also apply to input box
                  height: 50,
                },
              }}
            />
            <TextField
              label="Password"
              type={showPassword ? "text" : "password"}
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              sx={{
                mt: 3, // margin-top
                // marginLeft: 8,
                width: 295,

                "& .MuiOutlinedInput-root": {
                  // borderRadius: "20px", // also apply to input box
                  height: 50,
                },
              }}
              InputProps={{
                endAdornment: (
                  <InputAdornment position="end">
                    <IconButton onClick={handleTogglePassword} edge="end">
                      {showPassword ? <VisibilityOff /> : <Visibility />}
                    </IconButton>
                  </InputAdornment>
                ),
              }}
            />
            <Typography
              sx={{
                textAlign: "left",
                ml: 8,
                mt: 1,
                color: "rgb(58, 50, 192)",
                cursor: "pointer",
              }}
            >
              Forgot Password?
            </Typography>
            <Button
              type="submit"
              variant="outlined"
              sx={{
                mt: 3, // margin-top
                // marginLeft: ,
                width: 295,
                height: 45,
                borderRadius: "4px",
                backgroundColor: "rgba(79, 70, 229, 1)",
                color: "rgba(255, 255, 255, 1)",
                "&:hover": {
                  backgroundColor: "rgba(67, 56, 202, 1)", // Optional hover effect
                },
                "& .MuiOutlinedInput-root": {
                  borderRadius: "20px", // also apply to input box
                },
              }}
            >
              Log In
            </Button>
            <Typography
              sx={{
                textAlign: "center",
                color: "red",
              }}
            >
              {errorMessage && <p>{errorMessage}</p>}
            </Typography>
          </form>
        </Paper>
      </Box>
    </Box>
  );
};

export default Login;

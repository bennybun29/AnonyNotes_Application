<?php
// Database connection details
$servername = "localhost";
$username = "root"; // default username for XAMPP
$password = "";     // default password for XAMPP (usually empty)
$dbname = "anonynotes_db2"; // Replace with your database name

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Set content type to JSON
header('Content-Type: application/json');

// Retrieve data from the request
$email_username = isset($_POST['email_username']) ? trim($_POST['email_username']) : '';
$password = isset($_POST['password']) ? trim($_POST['password']) : '';

// Check if fields are filled
if (empty($email_username) || empty($password)) {
    echo json_encode(["status" => "error", "message" => "Missing required fields."]);
    exit();
}

// Query the database for the user by email or username
$query = "SELECT * FROM users WHERE email = ? OR user_name = ?";
$stmt = $conn->prepare($query);

if ($stmt === false) {
    echo json_encode(["status" => "error", "message" => "Failed to prepare statement."]);
    exit();
}

$stmt->bind_param('ss', $email_username, $email_username);
$stmt->execute();
$result = $stmt->get_result();

// Check if user exists
if ($result->num_rows > 0) {
    $user = $result->fetch_assoc();
    
    // Verify the password
    if (password_verify($password, $user['password'])) {
        // Return success response with user details
        unset($user['password']); // Do not send the password in the response
        echo json_encode(["status" => "success", "message" => "Login successful", "user" => $user]);
    } else {
        echo json_encode(["status" => "error", "message" => "Incorrect password."]);
    }
} else {
    echo json_encode(["status" => "error", "message" => "User not found."]);
}

// Close statement and connection
$stmt->close();
$conn->close();
?>

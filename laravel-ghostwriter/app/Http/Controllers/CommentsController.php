<?php

namespace App\Http\Controllers;

use App\Models\Comments;
use Illuminate\Http\Request;

class CommentsController extends Controller
{
    /**
     * Display a listing of the comments.
     */
    public function index()
    {
        // Retrieves all comments from the database
        $comments = Comments::all();

        return response()->json($comments);
    }

    /**
     * Store a newly created comment.
     */
    public function store(Request $request)
    {
        // Validates the incoming request data
        $request->validate([
            'note_id' => 'required|integer|exists:notes,note_id', // Ensures note_id exists in notes table
            'user_name' => 'required|string|exists:users,user_name', // Ensures user_name exists in users table
            'content' => 'required|string', // Ensures content is a string
            'anonymous' => 'required|boolean', // Ensures anonymous is a boolean (true/false)
        ]);

        // Creates a new comment using validated data
        $comment = Comments::create($request->all());

        // Returns the newly created comment with a 201 (created) status
        return response()->json($comment, 201);
    }

    /**
     * Display the specified comment.
     */
    public function show($id)
    {
        // Find the comment by its ID
        $comment = Comments::find($id);

        // If the comment doesn't exist, return a 404 error
        if (!$comment) {
            return response()->json(['message' => 'Comment not found'], 404);
        }

        // Return the found comment as JSON
        return response()->json($comment);
    }

    /**
     * Update the specified comment.
     */
    public function update(Request $request, $id)
    {
        // Find the comment by ID
        $comment = Comments::find($id);

        // If the comment doesn't exist, return a 404 error
        if (!$comment) {
            return response()->json(['message' => 'Comment not found'], 404);
        }

        // Validates the request data for updating
        $request->validate([
            'content' => 'sometimes|required|string', // Update the content if present, must be a string
            'anonymous' => 'sometimes|required|boolean', // Update the anonymous flag if present
        ]);

        // Update the comment with new data
        $comment->update($request->all());

        // Return the updated comment
        return response()->json($comment);
    }

    /**
     * Remove the specified comment.
     */
    public function destroy($id)
    {
        // Find the comment by ID
        $comment = Comments::find($id);

        // If the comment doesn't exist, return a 404 error
        if (!$comment) {
            return response()->json(['message' => 'Comment not found'], 404);
        }

        // Delete the comment
        $comment->delete();

        // Return a success message
        return response()->json(['message' => 'Comment deleted successfully']);
    }
}

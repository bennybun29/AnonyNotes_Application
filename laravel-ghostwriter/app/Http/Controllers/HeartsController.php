<?php

namespace App\Http\Controllers;

use App\Models\Hearts;
use Illuminate\Http\Request;

class HeartsController extends Controller
{
    /**
     * Display a listing of the hearts.
     */
    public function index()
    {
        // Retrieves all hearts from the database
        $hearts = Hearts::all();

        return response()->json($hearts);
    }

    /**
     * Store a newly created heart (like).
     */
    public function store(Request $request)
    {
        // Validates the incoming request data
        $request->validate([
            'comment_id' => 'nullable|integer|exists:comments,comment_id', // If present, must exist in comments
            'note_id' => 'nullable|integer|exists:notes,note_id', // If present, must exist in notes
            'user_name' => 'required|string|exists:users,user_name', // Must reference an existing user
        ]);

        // Creates a new heart using validated data
        $heart = Hearts::create($request->all());

        // Returns the newly created heart with a 201 (created) status
        return response()->json($heart, 201);
    }

    /**
     * Display the specified heart.
     */
    public function show($id)
    {
        // Find the heart by its ID
        $heart = Hearts::find($id);

        // If the heart doesn't exist, return a 404 error
        if (!$heart) {
            return response()->json(['message' => 'Heart not found'], 404);
        }

        // Return the found heart as JSON
        return response()->json($heart);
    }

    /**
     * Remove the specified heart (like).
     */
    public function destroy($id)
    {
        // Find the heart by ID
        $heart = Hearts::find($id);

        // If the heart doesn't exist, return a 404 error
        if (!$heart) {
            return response()->json(['message' => 'Heart not found'], 404);
        }

        // Delete the heart (like)
        $heart->delete();

        // Return a success message
        return response()->json(['message' => 'Heart (like) removed successfully']);
    }
}

<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        // Create the notes table
        Schema::create('notes', function (Blueprint $table) {
            $table->id('note_id'); // Primary key for the notes table
            $table->string('user_name', 50); // Foreign key to reference the users table (user_name)
            $table->text('content'); // Note content
            $table->boolean('anonymous'); // Indicates if the note is anonymous
            $table->timestamps(); // Date when the note was created

            // Define foreign key constraint
            $table->foreign('user_name')
                  ->references('user_name')
                  ->on('users')
                  ->onDelete('cascade')
                  ->onUpdate('cascade');
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('notes');
    }
};

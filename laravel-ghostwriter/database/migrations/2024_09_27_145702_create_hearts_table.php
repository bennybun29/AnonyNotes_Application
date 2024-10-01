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
        // Create the hearts (likes) table
        Schema::create('hearts', function (Blueprint $table) {
            $table->id('heart_id'); // Primary key 
            $table->unsignedBigInteger('comment_id')->nullable(); // Foreign key -> comments (optional)
            $table->unsignedBigInteger('note_id')->nullable(); // Foreign key -> notes (optional)
            $table->string('user_name', 50); // Foreign key -> users table (user_name)
            $table->timestamps(); // Date when the heart was created

            // Foreign key constraints
            $table->foreign('comment_id')
                  ->references('comment_id')
                  ->on('comments')
                  ->onDelete('cascade')
                  ->onUpdate('cascade');

            $table->foreign('note_id')
                  ->references('note_id')
                  ->on('notes')
                  ->onDelete('cascade')
                  ->onUpdate('cascade');

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
        Schema::dropIfExists('hearts');
    }
};

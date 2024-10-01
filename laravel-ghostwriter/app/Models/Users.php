<?php

namespace App\Models;

use Illuminate\Contracts\Auth\MustVerifyEmail;
use Laravel\Sanctum\HasApiTokens;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Notifications\Notifiable;

class Users extends Authenticatable
{
    use HasApiTokens, HasFactory, Notifiable;

    //table associated with this model
    protected $table ='users';
    protected $primaryKey = 'user_id';

    //fillable fields for mass assignment
    protected $fillable = [
        'user_id',
        'user_name',
        'email',
        'password',
        'created_at',
        'profile_img',
        'bio',
        'remember_token',
    ];

    /**
     * The attributes that should be hidden for serialization.
     *
     * @var array<int, string>
     */
    protected $hidden = [
        'password',
        'remember_token',
    ];

    
    /**
     * Get the attributes that should be cast.
     *
     * @return array<string, string>
     */
    protected function casts(): array
    {
        return [
            'email_verified_at' => 'datetime',
            'password' => 'hashed',
        ];
    }

    //Relationships with other tables

    //relationship with the Notes model (User has many notes)
     public function notes()
     {
         return $this->hasMany(Notes::class, 'user_name', 'user_name');
     }
 
     //relationship with the Comments model (User has many comments)
     public function comments()
     {
         return $this->hasMany(Comments::class, 'user_name', 'user_name');
     }
 
     //relationship with the Hearts model (User can give many hearts)
     public function hearts()
     {
         return $this->hasMany(Hearts::class, 'user_name', 'user_name');
     }
}

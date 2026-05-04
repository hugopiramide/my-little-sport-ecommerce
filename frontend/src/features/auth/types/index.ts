export interface LoginRequest {
  username: string;
  password?: string;
}

export interface RegisterRequest {
  name: string;
  surname: string;
  username: string;
  email: string;
  birthday: string | Date;
  profileImgUrl?: string;
  password?: string;
}

export interface AuthResponse {
  token: string;
}

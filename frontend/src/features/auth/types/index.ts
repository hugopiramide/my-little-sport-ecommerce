export interface LoginRequest {
  username: string;
  password: string;
}

export interface RegisterRequest {
  name: string;
  surname: string;
  username: string;
  email: string;
  birthday: string | Date;
  profileImgUrl?: string;
  password: string;
}

export interface UserDTO {
  username: string;
  name: string;
  surname: string;
  profileImgUrl?: string | null;
  birthday?: string | null;
  emailVerified: boolean;
  email: string;
  requiresVerification: boolean;
  verificationExpiresInSeconds: number;
}

export interface RegisterResponse {
  requiresVerification: boolean;
  user: UserDTO;
  verificationExpiresInSeconds: number;
}

export interface AuthResponse {
  token: string;
  user: UserDTO;
  requiresVerification: boolean;
  verificationExpiresInSeconds: number;
}

export interface VerifyEmailRequest {
  username: string;
  code: string;
}

export interface ResendVerificationRequest {
  username: string;
}

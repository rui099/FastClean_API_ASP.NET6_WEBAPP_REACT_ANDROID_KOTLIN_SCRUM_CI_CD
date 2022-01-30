export const TOKEN_KEY = "@fastclean-Token";
export const USER = "User:";

export const isAuthenticated = () => localStorage.getItem(TOKEN_KEY) !== null;

export const getToken = () => localStorage.getItem(TOKEN_KEY);

export const login = token => {
  localStorage.setItem(TOKEN_KEY, token);
};

export const setnome = user => {
  localStorage.setItem(USER, user);
};

export const logout = () => {
    clear();
    window.location.href = '/';
};

export const clear = () => {
  localStorage.removeItem(TOKEN_KEY);
  localStorage.removeItem(USER);
};

export const getUser = () => localStorage.getItem(USER);

export const getCargo = () => {
 
  let utilizador = JSON.parse(localStorage.getItem(USER));
  let cargo = utilizador.cargo;

  return cargo;
 }
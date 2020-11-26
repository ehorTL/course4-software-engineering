const globals = {
  // IMPORTANT! http:// protocol refixing solved the problem of "network error"
  // (+ CORS headers on server)
  remlib_api_host: "http://localhost:3000/remlib.com/api/",
  auth: "auth",
  user_by_id: "users/",
  register_reader: "reader",
};

const const_globals = Object.freeze(globals);

export { const_globals };

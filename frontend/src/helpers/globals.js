const globals = {
  // IMPORTANT! http:// protocol refixing solved the problem of "network error"
  // (+ CORS headers on server)
  remlib_api_host: "http://localhost:3000/remlib.com/api/",
  auth: "auth",
  user_by_id: "users/",
  register_reader: "reader",
  publication_subjects: "subjects",
  catalog_entries_init: "catalog_entries/popular",
  popular_publications: "publications/popular",
  get_ce_by_pid: "catalog_entry_by_publication/",
  get_ce_by_id: "catalog_entry/",
  managers: "managers/",
  add_manager: "user",
  get_reader: "user/",
  get_readers: "readers",
  reports: "reports/",
  stub_ebook: "http://localhost:3000/remlib.com/api/ebook",
  get_loan_cards_by_user_id: "loan_cards/user/",

  assets: {
    avatars: {
      no_photo: "/img/avatar-no-photo.png",
    },
  },
};

const const_globals = Object.freeze(globals);

export { const_globals };

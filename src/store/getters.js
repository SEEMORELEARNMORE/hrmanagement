const getters = {
  sidebar: (state) => state.app.sidebar,
  token: (state) => state.user.token,
  visitedViews: (state) => state.tagsView.visitedViews,
  cachedViews: (state) => state.tagsView.cachedViews,
  language: (state) => state.app.language,
  name: (state) => state.user.name,
  avatar: (state) => state.user.avatar,
  authority: (state) => state.user.authority,
  introduction: (state) => state.user.introduction,
  roles: (state) => state.user.roles,
  permission_routes: (state) => state.permission.routes,
  job_options: (state) => state.user.job_options,
  department_options: (state) => state.user.department_options,
  edu_level_options: (state) => state.user.edu_level_options,
  unread: (state) => state.user.unread,
  id: (state) => state.user.token.substr(-7),
}
export default getters

import { createRouter, createWebHistory } from 'vue-router'
const Home = () => import('../components/Home.vue')
const Login = () => import('../components/Login.vue')
const Register = () => import('../components/Register.vue')
const HomeMain = () => import('../views/HomeMain.vue')
const Article = () => import('../views/Article.vue')
const Backstage = () => import('../components/Backstage.vue')
const Personal = () => import('../views/Personal.vue')
const UserList = () => import('../views/UserList.vue')
const ArticleList = () => import('../views/ArticleList.vue')
const ArticleTypeList = () => import('../views/ArticleTypeList.vue')
const EditArticle = () => import('../views/EditArticle.vue')
const CommentList = () => import('../views/CommentList.vue')
const Activate = () => import('../components/Activate.vue')
const ResetPassword = () => import('../components/ResetPassword.vue')
const NotFound404 = () => import('../components/NotFound404.vue')

const routes = [
	{
		path: '/home',
		name: 'Home',
		component: Home,
		children: [{
			path: '/',
			name: 'HomeMain',
			component: HomeMain
		},
		{
			path: '/article/:id(\\d+)',
			name: 'Article',
			component: Article
		}],
		redirect: {
			name: 'HomeMain'
		}
	},
	{
		path: '/login',
		name: 'Login',
		component: Login
	},
	{
		path: '/register',
		name: 'Register',
		component: Register
	},
	{
		path: '/backstage/admin',
		name: 'Backstage',
		component: Backstage,
		children: [{
			path: '/backstage',
			name: 'Personal',
			component: Personal
		},
		{
			path: '/backstage/userList',
			name: 'UserList',
			component: UserList
		},
		{
			path: '/backstage/articleList',
			name: 'ArticleList',
			component: ArticleList
		},
		{
			path: '/backstage/editArticle',
			name: 'EditArticle',
			component: EditArticle
		},
		{
			path: '/backstage/articleTypeList',
			name: 'ArticleTypeList',
			component: ArticleTypeList
		},{
			path: '/backstage/commentList',
			name: 'CommentList',
			component: CommentList
		}],
		redirect: {
			name: 'Personal'
		}
	},
	{
		path: '/user/activate',
		name: 'Activate',
		component: Activate
	},
	{
		path: '/user/resetPassword',
		name: 'ResetPassword',
		component: ResetPassword
	},
	{
		path: '/:path(.*)',
		component: NotFound404
	}
]

const router = createRouter({
	history: createWebHistory(process.env.BASE_URL),
	routes
})

export default router

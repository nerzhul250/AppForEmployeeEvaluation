import Axios from 'axios'
import authHeader from './auth-header';


/**
 * Export:
 * axios as default
 * class Api
*/

const PROD_URL = 'http://pi2sis.icesi.edu.co/usoftapi';
const DEV_URL = 'http://localhost:8888/usoftapi';

const axios = Axios.create({
  baseURL: DEV_URL
});

export default axios


const BASE_ROUTES = {
  evaluations: "/evaluations",
  users: "/users",
  criterias: "/criteria",
  indicators: "/indicators",
  careerPlans: "/careerplans",
  areas: "/areas",
  positions:"/positions",
  periods:"/periods",
  roles:"/role",
  jira: "/jira/rest"
};


/**
* List all items
* @return {Promise}
*/
function list(){
  return axios.get(this.baseRoute,{ headers: authHeader() });
}

/**
* Create new item
* @param {Object} itemInfo the new Item
* @return {Promise}
*/
function create(itemInfo){
  return axios.post(this.baseRoute, itemInfo,{ headers: authHeader() });
}


/**
* Retrieve a single item
* @param {numbre} id
* @return {Promise}
*/
function get(id){
 return axios.get(this.baseRoute+"/"+id,{ headers: authHeader() });
}

/**
* Update an item
* @param {numbre} id the id of the item
* @param {Object} itemInfo the new Item information
*/
function update(id, itemInfo){
  return axios.put(this.baseRoute+"/"+id, itemInfo,{ headers: authHeader() });
}

/**
* Update an item for Beycker
*/
function updatebykr(itemInfo){
  return axios.patch(this.baseRoute, itemInfo,{ headers: authHeader() });
}

/**
* Update info of current user
*/
function updateProfile(itemInfo){
  return axios.patch(this.baseRoute+"/profile", itemInfo,{ headers: authHeader() });
}


/**
* Remove an item
* @param {number} id
* @return {Promise} 
*/
function remove(id){
  return axios.delete(this.baseRoute+"/"+id,{ headers: authHeader() });
}


/**
* Make request
* @param {String} url
* @param {String} method HTTP METHOD
* @param {object} data
* @param {obect} params URL params
* @return {Promise} 
*/
function request(url, method="get", data={}, params={}){
  return axios.request({url, method, data, params,headers:authHeader()});
}


// /**
// * Make a GET request
// */
// function GETMethod(url, config={}){
//   return axios.get(url, config);
// }

// /**
// * Make a POST request
// */
// function POSTMethod(url, data={}, config={}){
//   return axios.post(this.baseRoute +"/"+ url, data, config);
// }

/**
 * Conection to section of the server.
*/
export  class Api{
  constructor(baseRoute){
      this.baseRoute = baseRoute;
  }
  static ROUTES(){
      return BASE_ROUTES;
  }
}

Api.prototype.list = list;
Api.prototype.create = create;
Api.prototype.get = get;
Api.prototype.update = update;
Api.prototype.updatebykr = updatebykr;
Api.prototype.updateProfile = updateProfile;
Api.prototype.remove = remove;
Api.prototype.request = request;
//Api.prototype.GETMethod = GETMethod;
//Api.prototype.POSTMethod = POSTMethod;



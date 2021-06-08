import React, {Component} from "react";
import sendRequest from "./utils";
import {BrowserRouter, Link, Route} from "react-router-dom";
import ProductsForm from "./ProductsForm";

const PRODUCT_URL = "http://localhost:9000/api/product"
const CATEGORY_URL = "http://localhost:9000/api/category"

class Products extends Component {

  constructor(props) {
    super(props);
    this.state = {
      products: [],
      categories: new Map()
    }
  }

  async getProducts() {
    return await sendRequest (PRODUCT_URL, "GET");
  }

  async getCategories() {
    let categories = await sendRequest(CATEGORY_URL, "GET");
    let categoriesMap = new Map();
    categories.forEach(category => {
      categoriesMap.set(category.id, category);
    })
    return categoriesMap;
  }

  async componentDidMount() {
    const products = await this.getProducts();
    this.setState({products: products});
    const categories = await this.getCategories();
    this.setState({categories: categories});
  }

  getCategoryName(categoryId) {
    let category = this.state.categories.get(categoryId);
    return (category) ? category.name : "Brak kategorii"
  }

  render() {
    return (
      <div id={"products"}>
        <h1>Products</h1>
        <BrowserRouter>
          <Link to={"/productcreate"}>Create new product</Link>
          <Route exact path={"/productcreate"} component={ProductsForm}/>
          <h2>List of products:</h2>
          <ul>
            {this.state.products.map((product, index) => (
              <div key={index}>
                <h4>{product.id}: {product.name}</h4>
                <p>Description: {product.description}</p>
                <p>Category: {this.getCategoryName(product.categoryId)}</p>
              </div>
            ))}
          </ul>
        </BrowserRouter>
      </div>
    )
  }
}

export default Products;

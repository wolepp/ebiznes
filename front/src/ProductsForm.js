import {Component} from "react";
import sendRequest from "./utils";

const CATEGORY_URL = "http://localhost:9000/api/category"
const ADD_PRODUCT_URL = "http://localhost:9000/api/product"

class ProductsForm extends Component {
  constructor(props) {
    super(props);
    this.state = {
      categories: [],
      productData: {
        categoryId: 0,
        name: "",
        description: ""
      }
    }
    this.postRequest = this.postRequest.bind(this);
    // this.handleCategoryChange = this.handleCategoryChange.bind(this);
  }

  async getCategories() {
    return await sendRequest(CATEGORY_URL, "GET");
  }

  async componentDidMount() {
    const categories = await this.getCategories();
    this.setState({categories: categories});
  }

  async postRequest(event) {
    event.preventDefault();
    sendRequest(ADD_PRODUCT_URL, "POST", this.state.productData);
  }

  render() {
    return (
      <form onSubmit={this.postRequest}>
        <label>Name</label>
        <input
          type="text"
          name="user[name]"
          value={this.state.productData.name}
          onChange={e => this.setState({productData: {...this.state.productData, name: e.target.value}})}
        />

        <label>Description</label>
        <input
          type="text"
          name="user[description]"
          value={this.state.productData.description}
          onChange={e => this.setState({productData: {...this.state.productData, description: e.target.value}})}
        />

        <select
          name="user[categoryId]"
          value={this.state.productData.categoryId}
          onChange={e => this.setState({productData: {...this.state.productData, categoryId: Number(e.target.value)}})}
        >
          {this.state.categories.map((category, index) => (
            <option value={category.id}>{category.name}</option>
          ))}
        </select>

        <button>Add product</button>
      </form>
    )
  }
}

export default ProductsForm;

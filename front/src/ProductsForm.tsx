import {Component} from "react";
import sendRequest from "./utils";

const CATEGORY_URL = "http://localhost:9000/api/category"
const ADD_PRODUCT_URL = "http://localhost:9000/api/product"

type Product = {
  categoryId: number
  name: string
  description: string
}

type ProductX = {
  id: number
  categoryId: number
  name: string
  description: string
}

type Category = {
  id: number
  name: string
  parentCategoryId?: number
}

type PFState = {
  categories: Category[];
  productData: Product,
}

interface PFProps {
}

class ProductsForm extends Component<PFProps, PFState> {
  constructor(props: PFProps) {
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
    return await sendRequest<Category[]>(CATEGORY_URL, "GET");
  }

  async componentDidMount() {
    const categories = await this.getCategories();
    this.setState({categories: categories});
  }

  async postRequest(event: { preventDefault: () => void; }) {
    event.preventDefault();
    sendRequest<Product[]>(ADD_PRODUCT_URL, "POST", this.state.productData);
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

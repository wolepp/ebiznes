import {Component} from "react";

type Category = {
  name: string
}

type CategoriesState = {
  products: Category[]
}

interface CategoriesProps {
}

class Categories extends Component<CategoriesProps, CategoriesState> {
  render() {
    return (
      <div>
        <h1>Categories:</h1>
        <ul>
          <li>Kategoria 1</li>
          <li>Kategoria 2</li>
          <li>Kategoria 3</li>
        </ul>
      </div>
    )
  }
}

export default Categories;

import { Col, Container, Row } from "react-bootstrap";
import { useEffect, useState } from "react";
import { getCategories } from "../Services/CategoriesAPI";
import { Link } from "react-router-dom";
import { getProductsOfCategory } from "../Services/ProductAPI";
import { logDOM } from "@testing-library/react";

const Categories = () => {
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    getCategories()
      .then(data => setCategories(data));
  }, []);

  const resetCategories = () => {
    console.log("showing all products")
  }

  return (
    <Container className='border'>
      <Row className='border-bottom pb-3 mb-3'>
        <Col>
          <h4 className='text-center mt-2'>Categories</h4>
          <h5 className='mt-3'>
            <Link to="/products">Show all products</Link>
          </h5>
        </Col>
      </Row>
      <Row>
        <Col
          style={{ maxHeight: 'calc(100vh - 230px)', overflow: "auto" }}
        >
          {categories.map((cat) => (
            <h5 key={cat.id}>
              <Link to={`/products/${cat.id}`}>
                {cat.name}
              </Link>
            </h5>
          ))}
        </Col>
      </Row>
    </Container>
  );
};

export default Categories;

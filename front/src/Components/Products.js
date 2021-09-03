import { useEffect, useState } from "react";
import { getProducts } from "../Services/ProductAPI";
import { Col, Container, Row } from "react-bootstrap";
import ProductBox from "./ProductBox";

const Products = () => {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    getProducts()
      .then(data => setProducts(data));
  }, []);

  return (
    <Container>
      <Row>
        <Col>
          {products.map(p => (
            <ProductBox key={p.id} product={p} />
          ))}
        </Col>
      </Row>
    </Container>
  );
};

export default Products;

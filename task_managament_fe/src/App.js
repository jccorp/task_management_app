import React from "react";
import * as ReactBootstrap from 'react-bootstrap';
import TaskListCard from "./components/TaskListCard";

export default function BasicExample() {
  const { Container, Row, Col } = ReactBootstrap;

  return (
    <Container>
        <Row>
            <Col md={{ offset: 3, span: 6 }}>
                <Container>
                    <Row>
                        <Col md={{span: 2}}>
                            <h1>Tasker</h1>
                        </Col>
                        <Col md={{offset:8,span: 2}}>
                            Techformist
                        </Col>
                    </Row>
                </Container>
            </Col>
        </Row>
        <Row>
            <Col md={{ offset: 3, span: 6 }}> 
                <TaskListCard />
            </Col>
        </Row>
    </Container>
  );
}

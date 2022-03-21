import { render, screen } from '@testing-library/react';
import App from './App';

test('renders name of the app', () => {
  render(<App />);
  const nameElement = screen.getByText("Tasker");
  expect(nameElement).toBeInTheDocument();
});



import { render, screen,
waitForElementToBeRemoved } from '@testing-library/react';
import TaskListCard from './TaskListCard';

function mockFetch(data){
    return jest.fn().mockImplementation(()=>
        Promise.resolve({
            ok:true,
            json: () => data
        })
    );
}

test('render TaskListCard', async () =>{

    fetch = mockFetch([{id:1,description:"task 1",completed:false,dueDate:Date.now()}]);

    render(<TaskListCard />);

    expect(screen.getByText("Loading...")).toBeInTheDocument();
    
    expect(fetch).toHaveBeenCalledTimes(1);
    
    await waitForElementToBeRemoved(() => screen.getByText("Loading..."));

    const taskElement = screen.getByTestId("cont-item-1");
    expect(taskElement).toBeInTheDocument();
    
});
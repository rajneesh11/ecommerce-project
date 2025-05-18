import React, {useState, useEffect} from "react";
import {
    TableBody, TableContainer, Table, TableHeader, TableCell, TableRow, TableFooter, Avatar, Badge, Pagination,
} from "@windmill/react-ui";
import axios from "axios";

const OrdersTable = ({resultsPerPage, filter}) => {
    const [page, setPage] = useState(1);
    const [ordersData, setOrdersData] = useState([]);

    // pagination setup
    const [totalResults, setTotalResults] = useState(0);
    const [response, setResponse] = useState([]);

    // pagination change control
    function onPageChange(p) {
        setPage(p);
    }

    // on page change, load new sliced ordersData
    const loadOrders = async () => {
        const jwtToken = localStorage.getItem("jwtToken");
        try {
            setResponse(await axios.post("http://localhost:8080/api/order/orderListForSeller",
                null,
                {
                    headers: {
                        Authorization: `Bearer ${jwtToken}`,
                    },
                }));
            if (response.data) {
                setTotalResults(response.data.length);
                setOrdersData(response.data.slice((page - 1) * resultsPerPage, page * resultsPerPage));
            }
        } catch (error) {
            alert("An error occurred. Please try again.");
        }
    }

    // here you would make another server request for new ordersData
    useEffect(() => {
        loadOrders();
        // If Filters Applied
        if (filter === "paid") {
            setOrdersData(response
                .filter((order) => order.status === "Paid")
                .slice((page - 1) * resultsPerPage, page * resultsPerPage));
        }
        if (filter === "un-paid") {
            setOrdersData(response
                .filter((order) => order.status === "Un-paid")
                .slice((page - 1) * resultsPerPage, page * resultsPerPage));
        }
        if (filter === "completed") {
            setOrdersData(response
                .filter((order) => order.status === "Completed")
                .slice((page - 1) * resultsPerPage, page * resultsPerPage));
        }

        // if filters dosent applied
        if (filter === "all" || !filter) {
            setOrdersData(response.slice((page - 1) * resultsPerPage, page * resultsPerPage));
        }
    }, [page, resultsPerPage, filter]);

    return (<div>
        {/* Table */}
        {ordersData.length === 0
            ? <div className="mb-3 flex items-center justify-between">
                <p className="font-light truncate  text-gray-600 dark:text-gray-300">
                    No orders yet.
                </p>
            </div>
            :
            <TableContainer className="mb-8">
                <Table>
                    <TableHeader>
                        <tr>
                            <TableCell>Client</TableCell>
                            <TableCell>Order ID</TableCell>
                            <TableCell>Amount</TableCell>
                            <TableCell>Status</TableCell>
                            <TableCell>Date</TableCell>
                        </tr>
                    </TableHeader>
                    <TableBody>
                        {ordersData.map((user, i) => (<TableRow key={i}>
                            <TableCell>
                                <div className="flex items-center text-sm">
                                    <Avatar
                                        className="hidden mr-3 md:block"
                                        src={user.avatar}
                                        alt="User image"
                                    />
                                    <div>
                                        <p className="font-semibold">{user.name}</p>
                                    </div>
                                </div>
                            </TableCell>
                            <TableCell>
                                <span className="text-sm">#000{i}</span>
                            </TableCell>
                            <TableCell>
                                <span className="text-sm">$ {user.amount}</span>
                            </TableCell>
                            <TableCell>
                                <Badge
                                    type={user.status === "Un-paid" ? "danger" : user.status === "Paid" ? "success" : user.status === "Completed" ? "warning" : "neutral"}
                                >
                                    {user.status}
                                </Badge>
                            </TableCell>
                            <TableCell>
                  <span className="text-sm">
                    {new Date(user.date).toLocaleDateString()}
                  </span>
                            </TableCell>
                        </TableRow>))}
                    </TableBody>
                </Table>
                <TableFooter>
                    <Pagination
                        totalResults={totalResults}
                        resultsPerPage={resultsPerPage}
                        label="Table navigation"
                        onChange={onPageChange}
                    />
                </TableFooter>
            </TableContainer>
        }
    </div>);
};

export default OrdersTable;

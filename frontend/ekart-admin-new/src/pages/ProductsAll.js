import React, {useState, useEffect} from "react";
import PageTitle from "../components/Typography/PageTitle";
import {Link, NavLink} from "react-router-dom";
import {
    EditIcon,
    EyeIcon,
    GridViewIcon,
    HomeIcon,
    ListViewIcon,
    TrashIcon,
} from "../icons";
import {
    Card,
    CardBody,
    Label,
    Select,
    Button,
    TableBody,
    TableContainer,
    Table,
    TableHeader,
    TableCell,
    TableRow,
    TableFooter,
    Avatar,
    Badge,
    Pagination,
    Modal,
    ModalHeader,
    ModalBody,
    ModalFooter,
} from "@windmill/react-ui";
// import response from "../utils/demo/productData";
import Icon from "../components/Icon";
import {genRating} from "../utils/genarateRating";
import axios from "axios";

const ProductsAll = () => {
    const [view, setView] = useState("grid");

    // Table and grid productData handlling
    const [page, setPage] = useState(1);
    const [productData, setProductData] = useState([]);

    // pagination setup
    const [resultsPerPage, setResultsPerPage] = useState(10);
    const [totalResults, setTotalResults] = useState(0);

    // pagination change control
    function onPageChange(p) {
        setPage(p);
    }

    // on page change, load new sliced productData
    // here you would make another server request for new productData
    useEffect(() => {
        if (productData.length === 0) {
            getProductList();
        }
    }, [page, resultsPerPage, productData]);

    const getProductList = async () => {
        const jwtToken = localStorage.getItem("jwtToken");
        try {
            const response = await axios.get(
                "http://localhost:8080/api/product/getProductList",
                {
                    headers: {
                        Authorization: `Bearer ${jwtToken}`,
                    },
                }
            );
            if (response.data) {
                if (response.data.productList) {
                    setTotalResults(response.data.productList.length);
                    setProductData(
                        response.data.productList.slice(
                            (page - 1) * resultsPerPage,
                            page * resultsPerPage
                        )
                    );
                } /*else {
                    alert("No products found. Please add some products to continue.");
                }*/
            }
        } catch (error) {
            alert("An error occurred. Please try again.");
        }
    };

    const handleProductDelete = async () => {
        const jwtToken = localStorage.getItem("jwtToken");
        try {
            const response = await axios.delete(
                `http://localhost:8080/api/product/deleteProduct/${selectedDeleteProduct.id}`,
                {
                    headers: {
                        Authorization: `Bearer ${jwtToken}`,
                    },
                }
            );
            console.log(response);
            if (response.data) {
                if (response.status === 200) {
                    alert("Product deleted successfully.");
                } else {
                    alert("No products found. Please add some products to continue.");
                }
            }
        } catch (error) {
            alert("An error occurred. Please try again.");
        }
        closeModal();
        const updatedList = productData.filter(p => p.id !== selectedDeleteProduct.id);
        setProductData(updatedList);

        // If list is empty after deletion, refresh
        if (updatedList.length === 0) {
            getProductList();
        }
    };

    // Delete action model
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [selectedDeleteProduct, setSelectedDeleteProduct] = useState(null);

    async function openModal(productId) {
        let product = await productData.filter((product) => product.id === productId)[0];
        setSelectedDeleteProduct(product);
        setIsModalOpen(true);
    }

    function closeModal() {
        setIsModalOpen(false);
    }

    // Handle list view
    const handleChangeView = () => {
        if (view === "list") {
            setView("grid");
        }
        if (view === "grid") {
            setView("list");
        }
    };

    return (
        <div>
            <PageTitle>All Products</PageTitle>

            {/* Breadcum */}
            <div className="flex text-gray-800 dark:text-gray-300">
                <div className="flex items-center text-purple-600">
                    <Icon className="w-5 h-5" aria-hidden="true" icon={HomeIcon}/>
                    <NavLink exact to="/app/dashboard" className="mx-2">
                        Dashboard
                    </NavLink>
                </div>
                {">"}
                <p className="mx-2">All Products</p>
            </div>

            {/* Sort */}
            <Card className="mt-5 mb-5 shadow-md">
                <CardBody>
                    <div className="flex items-center justify-between">
                        <div className="flex items-center">
                            <p className="text-sm text-gray-600 dark:text-gray-400">
                                All Products
                            </p>

                            <Label className="mx-3">
                                <Select className="py-3">
                                    <option>Sort by</option>
                                    <option>Asc</option>
                                    <option>Desc</option>
                                </Select>
                            </Label>

                            <Label className="mx-3">
                                <Select className="py-3">
                                    <option>Filter by Category</option>
                                    <option>Electronics</option>
                                    <option>Cloths</option>
                                    <option>Mobile Accerssories</option>
                                </Select>
                            </Label>

                            <Label className="mr-8">
                                {/* <!-- focus-within sets the color for the icon when input is focused --> */}
                                <div
                                    className="relative text-gray-500 focus-within:text-purple-600 dark:focus-within:text-purple-400">
                                    <input
                                        className="py-3 pr-5 text-sm text-black dark:text-gray-300 dark:border-gray-600 dark:bg-gray-700 focus:border-purple-400 focus:outline-none focus:shadow-outline-purple dark:focus:shadow-outline-gray form-input"
                                        placeholder="Number of Results"
                                        value={resultsPerPage}
                                        onChange={(e) => setResultsPerPage(e.target.value)}
                                    />
                                    <div
                                        className="absolute inset-y-0 right-0 flex items-center mr-3 pointer-events-none">
                                        {/* <SearchIcon className="w-5 h-5" aria-hidden="true" /> */}
                                        Results on {`${view}`}
                                    </div>
                                </div>
                            </Label>
                        </div>
                        <div className="">
                            <Button
                                icon={view === "list" ? ListViewIcon : GridViewIcon}
                                className="p-2"
                                aria-label="Edit"
                                onClick={handleChangeView}
                            />
                        </div>
                    </div>
                </CardBody>
            </Card>

            {/* Delete product model */}
            <Modal isOpen={isModalOpen} onClose={closeModal}>
                <ModalHeader className="flex items-center">
                    {/* <div className="flex items-center"> */}
                    <Icon icon={TrashIcon} className="w-6 h-6 mr-3"/>
                    Delete Product
                    {/* </div> */}
                </ModalHeader>
                <ModalBody>
                    Make sure you want to delete product{" "}
                    {selectedDeleteProduct && `"${selectedDeleteProduct.name}"`}
                </ModalBody>
                <ModalFooter>
                    <div className="hidden sm:block">
                        <Button layout="outline" onClick={closeModal}>
                            Cancel
                        </Button>
                    </div>
                    <div className="hidden sm:block">
                        <Button onClick={handleProductDelete}>Delete</Button>
                    </div>
                    <div className="block w-full sm:hidden">
                        <Button block size="large" layout="outline" onClick={closeModal}>
                            Cancel
                        </Button>
                    </div>
                    <div className="block w-full sm:hidden">
                        <Button block size="large">
                            Delete
                        </Button>
                    </div>
                </ModalFooter>
            </Modal>

            {/* Product Views */}
            {productData.length === 0
                ? <div className="mb-3 flex items-center justify-between">
                    <p className="font-light truncate  text-gray-600 dark:text-gray-300">
                        No Products Found!!! Please add some products to continue.
                    </p>
                </div>
                : (
                    view === "list" ? (
                        <>
                            <TableContainer className="mb-8">
                                <Table>
                                    <TableHeader>
                                        <tr>
                                            <TableCell>Name</TableCell>
                                            <TableCell>Stock</TableCell>
                                            {/* <TableCell>Rating</TableCell> */}
                                            <TableCell>Stock Quantity</TableCell>
                                            <TableCell>Price</TableCell>
                                            <TableCell>Action</TableCell>
                                        </tr>
                                    </TableHeader>
                                    <TableBody>
                                        {productData.map((product) => (
                                            <TableRow key={product.id}>
                                                <TableCell>
                                                    <div className="flex items-center text-sm">
                                                        <Avatar
                                                            className="hidden mr-4 md:block"
                                                            src={product.imageUrl}
                                                            alt="Product image"
                                                        />
                                                        <div>
                                                            <p className="font-semibold">{product.name}</p>
                                                        </div>
                                                    </div>
                                                </TableCell>
                                                <TableCell>
                                                    <Badge
                                                        type={product.stockQuantity > 0 ? "success" : "danger"}
                                                    >
                                                        {product.stockQuantity > 0
                                                            ? "In Stock"
                                                            : "Out of Stock"}
                                                    </Badge>
                                                </TableCell>
                                                {/* <TableCell className="text-sm">
                                                  {genRating(product.rating, product.reviews.length, 5)}
                                                </TableCell> */}
                                                <TableCell className="text-sm">
                                                    {product.stockQuantity}
                                                </TableCell>
                                                <TableCell className="text-sm">₹ {product.price}</TableCell>
                                                <TableCell>
                                                    <div className="flex">
                                                        <Link to={`/app/product/${product.id}`}>
                                                            <Button
                                                                icon={EyeIcon}
                                                                className="mr-3"
                                                                aria-label="Preview"
                                                            />
                                                        </Link>
                                                        <Button
                                                            icon={EditIcon}
                                                            className="mr-3"
                                                            layout="outline"
                                                            aria-label="Edit"
                                                        />
                                                        <Button
                                                            icon={TrashIcon}
                                                            layout="outline"
                                                            onClick={() => openModal(product.id)}
                                                            aria-label="Delete"
                                                        />
                                                    </div>
                                                </TableCell>
                                            </TableRow>
                                        ))}
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
                        </>
                    ) : (
                        <>
                            {/* Card list */}
                            <div className="grid gap-4 grid-cols-2 md:grid-cols-3 lg:grid-cols-4 mb-8">
                                {productData.map((product) => (
                                    <div className="" key={product.id}>
                                        <Card>
                                            <img
                                                className="object-cover w-full"
                                                src={product.imageUrl}
                                                alt="product"
                                            />
                                            <CardBody>
                                                <div className="mb-3 flex items-center justify-between">
                                                    <p className="font-semibold truncate  text-gray-600 dark:text-gray-300">
                                                        {product.name}
                                                    </p>
                                                    <Badge
                                                        type={product.stockQuantity > 0 ? "success" : "danger"}
                                                        className="whitespace-nowrap"
                                                    >
                                                        <p className="break-normal">
                                                            {product.stockQuantity > 0
                                                                ? `In Stock`
                                                                : "Out of Stock"}
                                                        </p>
                                                    </Badge>
                                                </div>

                                                <p className="mb-2 text-purple-500 font-bold text-lg">
                                                    ₹ {product.price}
                                                </p>

                                                <p className="mb-8 text-gray-600 dark:text-gray-400">
                                                    {product.description}
                                                </p>

                                                <div className="flex items-center justify-between">
                                                    <div>
                                                        <Link to={`/app/product/${product.id}`}>
                                                            <Button
                                                                icon={EyeIcon}
                                                                className="mr-3"
                                                                aria-label="Preview"
                                                                size="small"
                                                            />
                                                        </Link>
                                                    </div>
                                                    <div>
                                                        <Button
                                                            icon={EditIcon}
                                                            className="mr-3"
                                                            layout="outline"
                                                            aria-label="Edit"
                                                            size="small"
                                                        />
                                                        <Button
                                                            icon={TrashIcon}
                                                            layout="outline"
                                                            aria-label="Delete"
                                                            onClick={() => openModal(product.id)}
                                                            size="small"
                                                        />
                                                    </div>
                                                </div>
                                            </CardBody>
                                        </Card>
                                    </div>
                                ))}
                            </div>

                            <Pagination
                                totalResults={totalResults}
                                resultsPerPage={resultsPerPage}
                                label="Table navigation"
                                onChange={onPageChange}
                            />
                        </>
                    )
                )}
        </div>
    );
};

export default ProductsAll;

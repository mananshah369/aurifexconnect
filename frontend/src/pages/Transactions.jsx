import { useState, useRef, useEffect } from "react";
import { useAppContext } from "../context/AppContext";
import { motion } from "framer-motion";
import { FiDownload, FiPlusCircle, FiChevronDown } from "react-icons/fi";
import { TextField, Autocomplete, Box } from "@mui/material";

const names = [
  "Oliver Hansen",
  "Van Henry",
  "April Tucker",
  "Ralph Hubbard",
  "Omar Alexander",
  "Carlos Abbott",
  "Miriam Wagner",
  "Bradley Wilkerson",
  "Virginia Andrews",
  "Kelly Snyder",
];

const tableData = [
  {
    date: "25-05-2025",
    name: "Oliver Hansen",
    amount: 2500,
    status: "Pending",
  },
  { date: "30-10-2021", name: "Van Henry", amount: 3200, status: "Completed" },
  { date: "24-02-2024", name: "April Tucker", amount: 1300, status: "Pending" },
  {
    date: "12-01-2020",
    name: "Ralph Hubbard",
    amount: 1700,
    status: "Completed",
  },
  {
    date: "12-03-2022",
    name: "Omar Alexander",
    amount: 1100,
    status: "Pending",
  },
  {
    date: "18-04-2024",
    name: "Carlos Abbott",
    amount: 200,
    status: "Completed",
  },
  {
    date: "05-05-2023",
    name: "Miriam Wagner",
    amount: 1600,
    status: "Pending",
  },
  {
    date: "14-04-2023",
    name: "Bradley Wilkerson",
    amount: 1500,
    status: "Completed",
  },
  {
    date: "29-01-2024",
    name: "Virginia Andrews",
    amount: 2500,
    status: "Pending",
  },
  {
    date: "31-05-2022",
    name: "Kelly Snyder",
    amount: 5500,
    status: "Completed",
  },
];

function Transactions() {
  const [personName, setPersonName] = useState("");
  const [filteredData, setFilteredData] = useState(tableData);

  ////////////////////Status DropDown states///////////////////////

  const [filter, setFilter] = useState("Select Status");
  const [isOpen, setIsOpen] = useState(false);
  const dropDownRef = useRef(null);

  const handleOptionClick = (status) => {
    setFilter(status);
    setIsOpen(false);
  };

  //Close dropdown if clicked outside

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (dropDownRef.current && !dropDownRef.current.contains(event.target)) {
        setIsOpen(false);
      }
    };
    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, []);

  const handleFilterName = () => {
    const filtered = tableData.filter((row) => {
      const matchName = personName ? row.name === personName : true;
      return matchName;
    });
    setFilteredData(filtered);
  };

  const handleFilterStatus = () => {
    const filtered = tableData.filter((row) => {
      // const matchName = personName ? row.name === personName : true;
      const matchStatus = filter ? row.status === filter : true;
      return matchStatus;
    });

    setFilteredData(filtered);
  };

  const handleFilter = () => {
    handleFilterName();
    handleFilterStatus();
  };

  //////////////////Transaction type////////////////////

  const [transactionType, setTransactionType] = useState("purchase");

  /////////////////////////////////////////////////////////////

  const { categories, accounts, addTransaction } = useAppContext();
  // const [filterOpen, setFilterOpen] = useState(false);
  // const [searchTerm, setSearchTerm] = useState("");
  // const [searchStatus, setSearchStatus] = useState("");

  // Filter states
  // const [typeFilter, setTypeFilter] = useState("all");
  // const [categoryFilter, setCategoryFilter] = useState("all");
  // const [accountFilter, setAccountFilter] = useState("all");
  // const [dateFilter, setDateFilter] = useState("all");

  // Apply filters
  // const filteredTransactions = transactions.filter((transaction) => {
  // Search term
  // if (
  //   searchTerm &&
  //   !transaction.description.toLowerCase().includes(searchTerm.toLowerCase())
  // ) {
  //   return false;
  // }

  // Type filter
  // if (typeFilter !== "all" && transaction.type !== typeFilter) {
  //   return false;
  // }

  // Category filter
  // if (categoryFilter !== "all" && transaction.category !== categoryFilter) {
  //   return false;
  // }

  // Account filter
  // if (accountFilter !== "all" && transaction.account !== accountFilter) {
  //   return false;
  // }

  // Date filter (simplified)
  //   if (dateFilter === "today") {
  //     const today = new Date().toISOString().split("T")[0];
  //     const transactionDate = new Date(transaction.date)
  //       .toISOString()
  //       .split("T")[0];
  //     if (today !== transactionDate) {
  //       return false;
  //     }
  //   } else if (dateFilter === "week") {
  //     const weekAgo = new Date();
  //     weekAgo.setDate(weekAgo.getDate() - 7);
  //     if (new Date(transaction.date) < weekAgo) {
  //       return false;
  //     }
  //   } else if (dateFilter === "month") {
  //     const monthAgo = new Date();
  //     monthAgo.setMonth(monthAgo.getMonth() - 1);
  //     if (new Date(transaction.date) < monthAgo) {
  //       return false;
  //     }
  //   }

  //   return true;
  // });

  // Add new transaction modal state
  const [showAddModal, setShowAddModal] = useState(false);
  const [newTransaction, setNewTransaction] = useState({
    description: "",
    amount: "",
    type: "expense",
    category: "",
    account: "",
  });

  const handleAddTransaction = () => {
    try {
      //setShowAddModal(true);
      console.log("Transaction added:");
      if (
        newTransaction.description &&
        newTransaction.amount &&
        newTransaction.category &&
        newTransaction.account
      ) {
        addTransaction({
          ...newTransaction,
          amount: parseFloat(newTransaction.amount),
          status: "completed",
        });

        setNewTransaction({
          description: "",
          amount: "",
          type: "expense",
          category: "",
          account: "",
        });
      } else {
        alert("Please fill in all fields.");
      }
    } catch (err) {
      console.error("Add Transaction Error:", err);
    }
  };

  const pageVariants = {
    initial: { opacity: 0, y: 20 },
    animate: { opacity: 1, y: 0 },
  };

  // console.log("Hi");
  // console.log(showAddModal);
  return (
    <motion.div
      variants={pageVariants}
      initial="initial"
      animate="animate"
      transition={{ duration: 0.5 }}
    >
      {/* //Header above Table */}
      <div className="md:flex md:items-center md:justify-between mb-6">
        <div className="flex-1 min-w-0">
          <h1 className="text-2xl font-bold leading-7 text-neutral-900 sm:text-3xl sm:leading-9 sm:truncate">
            Transactions
          </h1>
        </div>
        <div className="mt-4 flex md:mt-0 md:ml-4">
          {/* <button
            className="btn btn-secondary mr-3 flex items-center"
            onClick={() => setFilterOpen(!filterOpen)}
          >
            <FiFilter className="mr-2 h-4 w-4" />
            Filter
            <FiChevronDown
              className={`ml-2 h-4 w-4 transition-transform ${
                filterOpen ? "rotate-180" : ""
              }`}
            />
          </button> */}
          <button className="btn btn-secondary mr-3">
            <FiDownload className="mr-2 h-4 w-4" />
            Export
          </button>
          <button
            className="btn btn-primary flex items-center"
            onClick={() => setShowAddModal(true)}
          >
            <FiPlusCircle className="mr-2 h-4 w-4" />
            Add Transaction
          </button>
        </div>
      </div>

      {/* /////Types///// */}

      <div className="card mb-8">
        <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
          <button
            className={`py-3 px-4 text-center rounded-lg border w-[300px]- md:w-[260px]- ${
              transactionType === "purchase"
                ? "bg-primary-50 border-primary-300 text-primary-700"
                : "bg-white border-neutral-300 text-neutral-700 hover:bg-neutral-50"
            }`}
            onClick={() => setTransactionType("purchase")}
          >
            <h3 className="font-medium">Purchase</h3>
            {/* <p className="text-sm text-neutral-500 mt-1">Monthly comparison</p> */}
          </button>

          <button
            className={`py-3 px-4 text-center rounded-lg border w-[300px]- md:w-[260px]- ${
              transactionType === "sales"
                ? "bg-primary-50 border-primary-300 text-primary-700"
                : "bg-white border-neutral-300 text-neutral-700 hover:bg-neutral-50"
            }`}
            onClick={() => setTransactionType("sales")}
          >
            <h3 className="font-medium">Sales</h3>
            {/* <p className="text-sm text-neutral-500 mt-1">By category</p> */}
          </button>

          <button
            className={`py-3 px-4 text-center rounded-lg border  w-[300px]- md:w-[260px]- ${
              transactionType === "receipt"
                ? "bg-primary-50 border-primary-300 text-primary-700"
                : "bg-white border-neutral-300 text-neutral-700 hover:bg-neutral-50"
            }`}
            onClick={() => setTransactionType("receipt")}
          >
            <h3 className="font-medium">Receipt</h3>
            {/* <p className="text-sm text-neutral-500 mt-1">
              Monthly profit trends
            </p> */}
          </button>

          <button
            className={`py-3 px-4 text-center rounded-lg border  w-[300px]- md:w-[260px]- ${
              transactionType === "payment"
                ? "bg-primary-50 border-primary-300 text-primary-700"
                : "bg-white border-neutral-300 text-neutral-700 hover:bg-neutral-50"
            }`}
            onClick={() => setTransactionType("payment")}
          >
            <h3 className="font-medium">Payment</h3>
          </button>
        </div>
      </div>

      {/* Search and filters */}
      <div className="mb-6  flex ">
        {/* <div className="relative">
          <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
            <FiSearch className="h-5 w-5 text-neutral-400" />
          </div>
          <input
            type="text"
            className="input pl-10 w-[570px]"
            placeholder="Search transactions..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </div> */}

        <Box
          sx={{ m: 1, width: 570, display: "flex", justifyContent: "center" }}
        >
          <Autocomplete
            options={[...new Set(tableData.map((row) => row.name))]}
            value={personName || null}
            onChange={(event, newValue) => {
              event.preventDefault();
              setPersonName(newValue);
            }}
            size="small"
            sx={{
              width: "100%",
              "& .MuiInputBase-root": {
                // height: 36,
                backgroundColor: "white",
              },
            }}
            // renderTags={(value, getTagProps) =>
            //   value.map((option, index) => (
            //     <Chip
            //       size="small"
            //       variant="outlined"
            //       key={option}
            //       label={option}
            //       {...getTagProps({ index })}
            //     />
            //   ))
            // }
            renderInput={(params) => (
              <TextField
                {...params}
                variant="outlined"
                label="Select names"
                placeholder="Search..."
                // sx={{
                //   "& .MuiInputBase-input": {
                //     textAlign: "center",
                //   },
                // }}
              />
            )}
          />
        </Box>

        {/* <div className="relative">
          <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
            <FiSearch className="h-5 w-5 text-neutral-400" />
          </div>
          <input
            type="text"
            className="input pl-10 w-[570px]"
            placeholder="Search Status..."
            value={searchStatus}
            onChange={(e) => setSearchStatus(e.target.value)}
          />
        </div> */}

        <div
          className="relative inline-block text-left mt-1.5"
          ref={dropDownRef}
        >
          <button
            onClick={() => setIsOpen(!isOpen)}
            className="inline-flex justify-between items-center h-[40px] w-56 px-4 py-2 bg-white text-sm font-medium text-neutral-700 border border-neutral-300 rounded-lg shadow-sm hover:bg-neutral-50 focus:outline-none"
          >
            {filter}
            <FiChevronDown className="ml-2 h-5 w-5" />
          </button>

          {isOpen && (
            <div className="absolute z-10 mt-2 w-56 bg-white border border-neutral-200 rounded-lg shadow-lg">
              <button
                onClick={() => handleOptionClick("Pending")}
                className="w-full text-left px-4 py-2 text-sm hover:bg-neutral-100"
              >
                Pending
              </button>
              <button
                onClick={() => handleOptionClick("Completed")}
                className="w-full text-left px-4 py-2 text-sm hover:bg-neutral-100"
              >
                Completed
              </button>
            </div>
          )}
        </div>

        <button
          className="btn btn-primary flex items-center h-[35px] w-[90px]  mt-2 ml-2"
          onClick={handleFilter}
        >
          {/* <FiPlusCircle className="mr-2 h-4 w-4" /> */}
          Search
        </button>

        {/* <button
          className="btn btn-secondary mr-3 flex items-center w-[570px]"
          onClick={() => setFilterOpen(!filterOpen)}
        >
          <FiFilter className="mr-2 h-4 w-4" />
          Filter
          <FiChevronDown
            className={`ml-2 h-4 w-4 transition-transform ${
              filterOpen ? "rotate-180" : ""
            }`}
          />
        </button> */}

        {/* {filterOpen && (
          <motion.div
            initial={{ opacity: 0, y: -10 }}
            animate={{ opacity: 1, y: 0 }}
            className="mt-4 grid grid-cols-1 md:grid-cols-4 gap-4"
          >
            <div>
              <label className="block text-sm font-medium text-neutral-700 mb-1">
                Type
              </label>
              <select
                className="input"
                value={typeFilter}
                onChange={(e) => setTypeFilter(e.target.value)}
              >
                <option value="all">All Types</option>
                <option value="income">Income</option>
                <option value="expense">Expense</option>
              </select>
            </div>
            <div>
              <label className="block text-sm font-medium text-neutral-700 mb-1">
                Category
              </label>
              <select
                className="input"
                value={categoryFilter}
                onChange={(e) => setCategoryFilter(e.target.value)}
              >
                <option value="all">All Categories</option>
                {categories.map((category) => (
                  <option key={category.id} value={category.name.toLowerCase()}>
                    {category.name}
                  </option>
                ))}
              </select>
            </div>
            <div>
              <label className="block text-sm font-medium text-neutral-700 mb-1">
                Account
              </label>
              <select
                className="input"
                value={accountFilter}
                onChange={(e) => setAccountFilter(e.target.value)}
              >
                <option value="all">All Accounts</option>
                {accounts.map((account) => (
                  <option key={account.id} value={account.name}>
                    {account.name}
                  </option>
                ))}
              </select>
            </div>
            <div>
              <label className="block text-sm font-medium text-neutral-700 mb-1">
                Date
              </label>
              <select
                className="input"
                value={dateFilter}
                onChange={(e) => setDateFilter(e.target.value)}
              >
                <option value="all">All Time</option>
                <option value="today">Today</option>
                <option value="week">Last 7 Days</option>
                <option value="month">Last 30 Days</option>
              </select>
            </div>
          </motion.div>
        )} */}
      </div>

      {/* Transactions table */}
      <div className="card overflow-hidden">
        <h3 className="text-lg font-semibold text-neutral-900 mb-6">
          {transactionType === "purchase" && "Purchase"}
          {transactionType === "sales" && "Sales"}
          {transactionType === "receipt" && "Receipt"}
          {transactionType === "payment" && "Payment"}
        </h3>

        <div className="h-96">
          {transactionType === "purchase" && (
            <div className="overflow-x-auto">
              <table className="min-w-full divide-y divide-neutral-200">
                <thead className="bg-neutral-50">
                  <tr>
                    <th className="px-6 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">
                      Date
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">
                      Description
                    </th>
                    {/* <th className="px-6 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">
                  Category
                </th> */}
                    {/* <th className="px-6 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">
                  Account
                </th> */}
                    <th className="px-6 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">
                      Amount
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">
                      Status
                    </th>
                  </tr>
                </thead>
                <tbody className="bg-white divide-y divide-neutral-200">
                  {filteredData.map((row) => (
                    <motion.tr
                      key={row.date}
                      whileHover={{ backgroundColor: "#f9fafb" }}
                      className="cursor-pointer"
                    >
                      {/* <td className="px-6 py-4 whitespace-nowrap text-sm text-neutral-500">
                    {format(new Date(row.date), "MMM dd, yyyy")}
                  </td> */}
                      <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-neutral-900">
                        {row.date}
                      </td>
                      {/* <td className="px-6 py-4 whitespace-nowrap text-sm text-neutral-500 capitalize">
                    {transaction.category}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-neutral-500">
                    {transaction.account}
                  </td> */}
                      {/* <td
                    className={`px-6 py-4 whitespace-nowrap text-sm font-medium text-right ${
                      transaction.type === "income"
                        ? "text-success-600"
                        : "text-error-600"
                    }`}
                  >
                    {transaction.type === "income" ? "+" : "-"}₹
                    {transaction.amount.toFixed(2)}
                  </td> */}
                      {/* <td className="px-6 py-4 whitespace-nowrap text-right text-sm">
                    <span
                      className={`badge ${
                        transaction.status === "completed"
                          ? "badge-success"
                          : transaction.status === "pending"
                          ? "badge-warning"
                          : "bg-neutral-100 text-neutral-800"
                      }`}
                    >
                      {transaction.status}
                    </span>
                  </td> */}
                      <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-neutral-900">
                        {row.name}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-neutral-900">
                        {row.amount}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-neutral-900">
                        {row.status}
                      </td>
                    </motion.tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}

          {transactionType === "sales" && (
            <div className="overflow-x-auto">
              <table className="min-w-full divide-y divide-neutral-200">
                <thead className="bg-neutral-50">
                  <tr>
                    <th className="px-6 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">
                      Date
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">
                      Description
                    </th>
                    {/* <th className="px-6 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">
                  Category
                </th> */}
                    {/* <th className="px-6 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">
                  Account
                </th> */}
                    <th className="px-6 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">
                      Amount
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">
                      Status
                    </th>
                  </tr>
                </thead>
                <tbody className="bg-white divide-y divide-neutral-200">
                  {filteredData.map((row) => (
                    <motion.tr
                      key={row.date + row.name}
                      whileHover={{ backgroundColor: "#f9fafb" }}
                      className="cursor-pointer"
                    >
                      {/* <td className="px-6 py-4 whitespace-nowrap text-sm text-neutral-500">
                    {format(new Date(row.date), "MMM dd, yyyy")}
                  </td> */}
                      <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-neutral-900">
                        {row.date}
                      </td>
                      {/* <td className="px-6 py-4 whitespace-nowrap text-sm text-neutral-500 capitalize">
                    {transaction.category}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-neutral-500">
                    {transaction.account}
                  </td> */}
                      {/* <td
                    className={`px-6 py-4 whitespace-nowrap text-sm font-medium text-right ${
                      transaction.type === "income"
                        ? "text-success-600"
                        : "text-error-600"
                    }`}
                  >
                    {transaction.type === "income" ? "+" : "-"}₹
                    {transaction.amount.toFixed(2)}
                  </td> */}
                      {/* <td className="px-6 py-4 whitespace-nowrap text-right text-sm">
                    <span
                      className={`badge ${
                        transaction.status === "completed"
                          ? "badge-success"
                          : transaction.status === "pending"
                          ? "badge-warning"
                          : "bg-neutral-100 text-neutral-800"
                      }`}
                    >
                      {transaction.status}
                    </span>
                  </td> */}
                      <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-neutral-900">
                        {row.name}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-neutral-900">
                        {row.amount}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-neutral-900">
                        {row.status}
                      </td>
                    </motion.tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}

          {transactionType === "receipt" && (
            <div className="overflow-x-auto">
              <table className="min-w-full divide-y divide-neutral-200">
                <thead className="bg-neutral-50">
                  <tr>
                    <th className="px-6 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">
                      Date
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">
                      Description
                    </th>
                    {/* <th className="px-6 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">
                  Category
                </th> */}
                    {/* <th className="px-6 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">
                  Account
                </th> */}
                    <th className="px-6 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">
                      Amount
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">
                      Status
                    </th>
                  </tr>
                </thead>
                <tbody className="bg-white divide-y divide-neutral-200">
                  {filteredData.map((row) => (
                    <motion.tr
                      key={row.date}
                      whileHover={{ backgroundColor: "#f9fafb" }}
                      className="cursor-pointer"
                    >
                      {/* <td className="px-6 py-4 whitespace-nowrap text-sm text-neutral-500">
                    {format(new Date(row.date), "MMM dd, yyyy")}
                  </td> */}
                      <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-neutral-900">
                        {row.date}
                      </td>
                      {/* <td className="px-6 py-4 whitespace-nowrap text-sm text-neutral-500 capitalize">
                    {transaction.category}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-neutral-500">
                    {transaction.account}
                  </td> */}
                      {/* <td
                    className={`px-6 py-4 whitespace-nowrap text-sm font-medium text-right ${
                      transaction.type === "income"
                        ? "text-success-600"
                        : "text-error-600"
                    }`}
                  >
                    {transaction.type === "income" ? "+" : "-"}₹
                    {transaction.amount.toFixed(2)}
                  </td> */}
                      {/* <td className="px-6 py-4 whitespace-nowrap text-right text-sm">
                    <span
                      className={`badge ${
                        transaction.status === "completed"
                          ? "badge-success"
                          : transaction.status === "pending"
                          ? "badge-warning"
                          : "bg-neutral-100 text-neutral-800"
                      }`}
                    >
                      {transaction.status}
                    </span>
                  </td> */}
                      <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-neutral-900">
                        {row.name}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-neutral-900">
                        {row.amount}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-neutral-900">
                        {row.status}
                      </td>
                    </motion.tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}

          {transactionType === "payment" && (
            <div className="overflow-x-auto">
              <table className="min-w-full divide-y divide-neutral-200">
                <thead className="bg-neutral-50">
                  <tr>
                    <th className="px-6 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">
                      Date
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">
                      Description
                    </th>
                    {/* <th className="px-6 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">
                  Category
                </th> */}
                    {/* <th className="px-6 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">
                  Account
                </th> */}
                    <th className="px-6 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">
                      Amount
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">
                      Status
                    </th>
                  </tr>
                </thead>
                <tbody className="bg-white divide-y divide-neutral-200">
                  {filteredData.map((row) => (
                    <motion.tr
                      key={row.date}
                      whileHover={{ backgroundColor: "#f9fafb" }}
                      className="cursor-pointer"
                    >
                      {/* <td className="px-6 py-4 whitespace-nowrap text-sm text-neutral-500">
                    {format(new Date(row.date), "MMM dd, yyyy")}
                  </td> */}
                      <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-neutral-900">
                        {row.date}
                      </td>
                      {/* <td className="px-6 py-4 whitespace-nowrap text-sm text-neutral-500 capitalize">
                    {transaction.category}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-neutral-500">
                    {transaction.account}
                  </td> */}
                      {/* <td
                    className={`px-6 py-4 whitespace-nowrap text-sm font-medium text-right ${
                      transaction.type === "income"
                        ? "text-success-600"
                        : "text-error-600"
                    }`}
                  >
                    {transaction.type === "income" ? "+" : "-"}₹
                    {transaction.amount.toFixed(2)}
                  </td> */}
                      {/* <td className="px-6 py-4 whitespace-nowrap text-right text-sm">
                    <span
                      className={`badge ${
                        transaction.status === "completed"
                          ? "badge-success"
                          : transaction.status === "pending"
                          ? "badge-warning"
                          : "bg-neutral-100 text-neutral-800"
                      }`}
                    >
                      {transaction.status}
                    </span>
                  </td> */}
                      <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-neutral-900">
                        {row.name}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-neutral-900">
                        {row.amount}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-neutral-900">
                        {row.status}
                      </td>
                    </motion.tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </div>
      </div>

      {/* Add Transaction Modal */}
      {showAddModal && (
        <div className="fixed inset-0 z-50 overflow-y-auto">
          <div className="flex items-end justify-center min-h-screen pt-4 px-4 pb-20 text-center sm:block sm:p-0">
            <div
              className="fixed inset-0 transition-opacity"
              aria-hidden="true"
            >
              <div className="absolute inset-0 bg-neutral-500 opacity-75"></div>
            </div>

            <motion.div
              // initial={{ opacity: 0, scale: 0.95, y: 20 }}
              // animate={{ opacity: 1, scale: 1, y: 0 }}
              className="inline-block align-bottom bg-white rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-lg sm:w-full z-[9999]"
            >
              <div className="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
                <div className="sm:flex sm:items-start">
                  <div className="mt-3 text-center sm:mt-0 sm:ml-4 sm:text-left w-full">
                    <h3 className="text-lg leading-6 font-medium text-neutral-900 mb-4">
                      Add New Transaction
                    </h3>

                    <div className="mt-4 space-y-4">
                      <div>
                        <label className="block text-sm font-medium text-neutral-700 mb-1">
                          Description
                        </label>
                        <input
                          type="text"
                          className="input"
                          value={newTransaction.description}
                          onChange={(e) =>
                            setNewTransaction({
                              ...newTransaction,
                              description: e.target.value,
                            })
                          }
                        />
                      </div>

                      <div>
                        <label className="block text-sm font-medium text-neutral-700 mb-1">
                          Amount
                        </label>
                        <input
                          type="number"
                          step="0.01"
                          className="input"
                          value={newTransaction.amount}
                          onChange={(e) =>
                            setNewTransaction({
                              ...newTransaction,
                              amount: e.target.value,
                            })
                          }
                        />
                      </div>

                      <div>
                        <label className="block text-sm font-medium text-neutral-700 mb-1">
                          Type
                        </label>
                        <div className="flex space-x-4">
                          <label className="inline-flex items-center">
                            <input
                              type="radio"
                              className="form-radio text-primary-600"
                              name="transactionType"
                              value="expense"
                              checked={newTransaction.type === "expense"}
                              onChange={() =>
                                setNewTransaction({
                                  ...newTransaction,
                                  type: "expense",
                                })
                              }
                            />
                            <span className="ml-2 text-sm text-neutral-700">
                              Expense
                            </span>
                          </label>
                          <label className="inline-flex items-center">
                            <input
                              type="radio"
                              className="form-radio text-primary-600"
                              name="transactionType"
                              value="income"
                              checked={newTransaction.type === "income"}
                              onChange={() =>
                                setNewTransaction({
                                  ...newTransaction,
                                  type: "income",
                                })
                              }
                            />
                            <span className="ml-2 text-sm text-neutral-700">
                              Income
                            </span>
                          </label>
                        </div>
                      </div>

                      <div>
                        <label className="block text-sm font-medium text-neutral-700 mb-1">
                          Category
                        </label>
                        <select
                          className="input"
                          value={newTransaction.category}
                          onChange={(e) =>
                            setNewTransaction({
                              ...newTransaction,
                              category: e.target.value,
                            })
                          }
                        >
                          <option value="">Select a category</option>
                          {categories
                            .filter((cat) => cat.type === newTransaction.type)
                            .map((category) => (
                              <option
                                key={category.id}
                                value={category.name.toLowerCase()}
                              >
                                {category.name}
                              </option>
                            ))}
                        </select>
                      </div>

                      <div>
                        <label className="block text-sm font-medium text-neutral-700 mb-1">
                          Account
                        </label>
                        <select
                          className="input"
                          value={newTransaction.account}
                          onChange={(e) =>
                            setNewTransaction({
                              ...newTransaction,
                              account: e.target.value,
                            })
                          }
                        >
                          <option value="">Select an account</option>
                          {accounts.map((account) => (
                            <option key={account.id} value={account.name}>
                              {account.name}
                            </option>
                          ))}
                        </select>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <div className="bg-neutral-50 px-4 py-3 sm:px-6 sm:flex sm:flex-row-reverse">
                <button
                  type="button"
                  className="btn btn-primary w-full sm:w-auto sm:ml-3"
                  onClick={handleAddTransaction}
                >
                  Add Transaction
                </button>
                <button
                  type="button"
                  className="btn btn-secondary w-full sm:w-auto mt-3 sm:mt-0"
                  onClick={() => setShowAddModal(false)}
                >
                  Cancel
                </button>
              </div>
            </motion.div>
          </div>
        </div>
      )}
    </motion.div>
  );
}

export default Transactions;

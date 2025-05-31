import { useState } from "react";
import { motion } from "framer-motion";
import {
  FiPlus,
  FiFilter,
  FiFileText,
  FiPrinter,
  FiDownload,
} from "react-icons/fi";
import { mockInvoices } from "../data/mockData";

function Invoices() {
  const [statusFilter, setStatusFilter] = useState("all");

  // Apply filters
  const filteredInvoices = mockInvoices.filter((invoice) => {
    if (statusFilter === "all") return true;
    return invoice.status === statusFilter;
  });

  // Summary calculations
  const totalPaid = mockInvoices
    .filter((invoice) => invoice.status === "paid")
    .reduce((sum, invoice) => sum + invoice.amount, 0);

  const totalUnpaid = mockInvoices
    .filter((invoice) => invoice.status === "unpaid")
    .reduce((sum, invoice) => sum + invoice.amount, 0);

  const totalDraft = mockInvoices
    .filter((invoice) => invoice.status === "draft")
    .reduce((sum, invoice) => sum + invoice.amount, 0);

  return (
    <motion.div
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.5 }}
    >
      <div className="md:flex md:items-center md:justify-between mb-6">
        <div className="flex-1 min-w-0">
          <h1 className="text-2xl font-bold leading-7 text-neutral-900 sm:text-3xl sm:leading-9 sm:truncate">
            Invoices
          </h1>
          <p className="mt-1 text-sm text-neutral-500">
            Manage and track your invoices
          </p>
        </div>
        <div className="mt-4 flex md:mt-0 md:ml-4">
          <button className="btn btn-primary flex items-center">
            <FiPlus className="mr-2 h-4 w-4" />
            Create Invoice
          </button>
        </div>
      </div>

      {/* Summary Cards */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
        <motion.div
          whileHover={{ y: -4 }}
          transition={{ duration: 0.2 }}
          className="card"
        >
          <div className="flex items-center justify-between">
            <div>
              <p className="text-sm font-medium text-neutral-500">Paid</p>
              <p className="mt-1 text-2xl font-semibold text-success-600">
                ₹{totalPaid.toFixed(2)}
              </p>
            </div>
            <div className="p-3 rounded-lg bg-success-100">
              <FiFileText className="h-6 w-6 text-success-600" />
            </div>
          </div>
          <p className="mt-4 text-sm text-neutral-500">
            {mockInvoices.filter((i) => i.status === "paid").length} invoices
          </p>
        </motion.div>

        <motion.div
          whileHover={{ y: -4 }}
          transition={{ duration: 0.2 }}
          className="card"
        >
          <div className="flex items-center justify-between">
            <div>
              <p className="text-sm font-medium text-neutral-500">Unpaid</p>
              <p className="mt-1 text-2xl font-semibold text-warning-600">
                ₹{totalUnpaid.toFixed(2)}
              </p>
            </div>
            <div className="p-3 rounded-lg bg-warning-100">
              <FiFileText className="h-6 w-6 text-warning-600" />
            </div>
          </div>
          <p className="mt-4 text-sm text-neutral-500">
            {mockInvoices.filter((i) => i.status === "unpaid").length} invoices
          </p>
        </motion.div>

        <motion.div
          whileHover={{ y: -4 }}
          transition={{ duration: 0.2 }}
          className="card"
        >
          <div className="flex items-center justify-between">
            <div>
              <p className="text-sm font-medium text-neutral-500">Draft</p>
              <p className="mt-1 text-2xl font-semibold text-neutral-600">
                ₹{totalDraft.toFixed(2)}
              </p>
            </div>
            <div className="p-3 rounded-lg bg-neutral-100">
              <FiFileText className="h-6 w-6 text-neutral-600" />
            </div>
          </div>
          <p className="mt-4 text-sm text-neutral-500">
            {mockInvoices.filter((i) => i.status === "draft").length} invoices
          </p>
        </motion.div>
      </div>

      {/* Filters */}
      <div className="flex flex-wrap gap-4 mb-6">
        <button
          className={`px-4 py-2 rounded-md text-sm font-medium ${
            statusFilter === "all"
              ? "bg-primary-100 text-primary-800"
              : "bg-white text-neutral-700 border border-neutral-300"
          }`}
          onClick={() => setStatusFilter("all")}
        >
          All Invoices
        </button>
        <button
          className={`px-4 py-2 rounded-md text-sm font-medium ${
            statusFilter === "paid"
              ? "bg-success-100 text-success-800"
              : "bg-white text-neutral-700 border border-neutral-300"
          }`}
          onClick={() => setStatusFilter("paid")}
        >
          Paid
        </button>
        <button
          className={`px-4 py-2 rounded-md text-sm font-medium ${
            statusFilter === "unpaid"
              ? "bg-warning-100 text-warning-800"
              : "bg-white text-neutral-700 border border-neutral-300"
          }`}
          onClick={() => setStatusFilter("unpaid")}
        >
          Unpaid
        </button>
        <button
          className={`px-4 py-2 rounded-md text-sm font-medium ${
            statusFilter === "draft"
              ? "bg-neutral-100 text-neutral-800"
              : "bg-white text-neutral-700 border border-neutral-300"
          }`}
          onClick={() => setStatusFilter("draft")}
        >
          Draft
        </button>
      </div>

      {/* Invoice List */}
      <div className="card overflow-hidden">
        <div className="overflow-x-auto">
          <table className="min-w-full divide-y divide-neutral-200">
            <thead className="bg-neutral-50">
              <tr>
                <th className="px-6 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">
                  Invoice #
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">
                  Client
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">
                  Issue Date
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">
                  Due Date
                </th>
                <th className="px-6 py-3 text-right text-xs font-medium text-neutral-500 uppercase tracking-wider">
                  Amount
                </th>
                <th className="px-6 py-3 text-center text-xs font-medium text-neutral-500 uppercase tracking-wider">
                  Status
                </th>
                <th className="px-6 py-3 text-right text-xs font-medium text-neutral-500 uppercase tracking-wider">
                  Actions
                </th>
              </tr>
            </thead>
            <tbody className="bg-white divide-y divide-neutral-200">
              {filteredInvoices.map((invoice) => (
                <motion.tr
                  key={invoice.id}
                  whileHover={{ backgroundColor: "#f9fafb" }}
                  className="cursor-pointer"
                >
                  <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-primary-600">
                    {invoice.id}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-neutral-900">
                    {invoice.client}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-neutral-500">
                    {invoice.issueDate}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-neutral-500">
                    {invoice.dueDate}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-right">
                    ₹{invoice.amount.toFixed(2)}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-center">
                    <span
                      className={`badge ${
                        invoice.status === "paid"
                          ? "badge-success"
                          : invoice.status === "unpaid"
                          ? "badge-warning"
                          : "bg-neutral-100 text-neutral-800"
                      }`}
                    >
                      {invoice.status}
                    </span>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                    <div className="flex justify-end space-x-2">
                      <button className="p-1 text-neutral-500 hover:text-primary-600">
                        <FiFileText className="h-4 w-4" />
                      </button>
                      <button className="p-1 text-neutral-500 hover:text-primary-600">
                        <FiPrinter className="h-4 w-4" />
                      </button>
                      <button className="p-1 text-neutral-500 hover:text-primary-600">
                        <FiDownload className="h-4 w-4" />
                      </button>
                    </div>
                  </td>
                </motion.tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </motion.div>
  );
}

export default Invoices;
